package com.project.dorm.controller.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.dorm.domain.DormService;
import com.project.dorm.domain.Result;
import com.project.dorm.domain.User;
import com.project.dorm.enums.ResultCode;
import com.project.dorm.service.DormServiceService;
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
 * @description: 维修controller
 * @date 2024/03/16 08:36
 */
@Controller
@ResponseBody
@RequestMapping("service")
public class DormServiceController {

    @Autowired
    private DormServiceService dormServiceService;
    @Autowired
    private UserService userService;

    /** 分页获取维修 */
    @PostMapping("getDormServicePage")
    public Result getDormServicePage(@RequestBody DormService dormService) {
        User user = userService.getById(TokenUtils.getUserIdByToken());
        if (dormService.getType() == 0) {
            dormService.setUserId(user.getId());
        }
        Page<DormService> page = new Page<>(dormService.getPageNumber(),dormService.getPageSize());
        QueryWrapper<DormService> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dormService.getBuild()),DormService::getBuild,dormService.getBuild())
                .like(StringUtils.isNotBlank(dormService.getRoom()),DormService::getRoom,dormService.getRoom())
                .eq(StringUtils.isNotBlank(dormService.getUserId()),DormService::getUserId,dormService.getUserId())
                .like(StringUtils.isNotBlank(dormService.getContent()),DormService::getContent,dormService.getContent())
                .eq(dormService.getState() != null,DormService::getState,dormService.getState());
        Page<DormService> dormServicePage = dormServiceService.page(page, queryWrapper);
        return Result.success(dormServicePage);
    }

    /** 根据id获取维修 */
    @GetMapping("getDormServiceById")
    public Result getDormServiceById(@RequestParam("id")String id) {
        DormService dormService = dormServiceService.getById(id);
        return Result.success(dormService);
    }

    /** 保存维修 */
    @PostMapping("saveDormService")
    public Result saveDormService(@RequestBody DormService dormService) {
        User user = userService.getById(TokenUtils.getUserIdByToken());
        dormService.setBuild(user.getBuild());
        dormService.setRoom(user.getRoom());
        dormService.setUserId(user.getId());
        boolean save = dormServiceService.save(dormService);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑维修 */
    @PostMapping("editDormService")
    public Result editDormService(@RequestBody DormService dormService) {
        boolean save = dormServiceService.updateById(dormService);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除维修 */
    @GetMapping("removeDormService")
    public Result removeDormService(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dormServiceService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("维修id不能为空！");
        }
    }

}
