package com.project.dorm.controller.apply;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.dorm.domain.DormApply;
import com.project.dorm.domain.Result;
import com.project.dorm.domain.User;
import com.project.dorm.enums.ResultCode;
import com.project.dorm.service.DormApplyService;
import com.project.dorm.service.UserService;
import com.project.dorm.utils.TokenUtils;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 换宿
controller
 * @date 2024/03/15 10:47
 */
@Controller
@ResponseBody
@RequestMapping("apply")
public class DormApplyController {

    @Autowired
    private DormApplyService dormApplyService;
    @Autowired
    private UserService userService;

    /** 分页获取换宿
     */
    @PostMapping("getDormApplyPage")
    public Result getDormApplyPage(@RequestBody DormApply dormApply) {
        String id = TokenUtils.getUserIdByToken();
        User user = userService.getById(id);
        if (dormApply.getType() == 0) {
            dormApply.setUserId(id);
        } else {
            dormApply.setBuild(user.getBuild());
        }
        Page<DormApply> page = new Page<>(dormApply.getPageNumber(),dormApply.getPageSize());
        QueryWrapper<DormApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(StringUtils.isNotBlank(dormApply.getUserId()), DormApply::getUserId,dormApply.getUserId())
            .like(StringUtils.isNotBlank(dormApply.getBuild()),DormApply::getBuild,dormApply.getBuild())
            .like(StringUtils.isNotBlank(dormApply.getRoom()),DormApply::getRoom,dormApply.getRoom())
            .like(StringUtils.isNotBlank(dormApply.getContent()),DormApply::getContent,dormApply.getContent())
            .eq(dormApply.getState() != null,DormApply::getState,dormApply.getState())
            .like(StringUtils.isNotBlank(dormApply.getCreateBy()),DormApply::getCreateBy,dormApply.getCreateBy());
        Page<DormApply> dormApplyPage = dormApplyService.page(page, queryWrapper);
        return Result.success(dormApplyPage);
    }

    /** 根据id获取换宿
    */
    @GetMapping("getDormApplyById")
    public Result getDormApplyById(@RequestParam("id")String id) {
        DormApply dormApply = dormApplyService.getById(id);
        return Result.success(dormApply);
    }

    /** 保存换宿
    */
    @PostMapping("saveDormApply")
    public Result saveDormApply(@RequestBody DormApply dormApply) {
        String id = TokenUtils.getUserIdByToken();
        dormApply.setUserId(id);
        User user = userService.getById(id);
        dormApply.setBuild(user.getBuild());
        dormApply.setRoom(user.getRoom());
        boolean save = dormApplyService.save(dormApply);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑换宿
    */
    @PostMapping("editDormApply")
    public Result editDormApply(@RequestBody DormApply dormApply) {
        boolean save = dormApplyService.updateById(dormApply);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除换宿*/
    @GetMapping("removeDormApply")
    public Result removeDormApply(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dormApplyService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("换宿id不能为空！");
        }
    }

}