package com.project.dorm.controller.worry;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.dorm.domain.DormWorry;
import com.project.dorm.domain.Result;
import com.project.dorm.domain.User;
import com.project.dorm.enums.ResultCode;
import com.project.dorm.service.DormWorryService;
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
 * @description: 违纪controller
 * @date 2024/03/16 10:18
 */
@Controller
@ResponseBody
@RequestMapping("worry")
public class DormWorryController {

    @Autowired
    private DormWorryService dormWorryService;
    @Autowired
    private UserService userService;

    /** 分页获取违纪 */
    @PostMapping("getDormWorryPage")
    public Result getDormWorryPage(@RequestBody DormWorry dormWorry) {
        User user = userService.getById(TokenUtils.getUserIdByToken());
        if (dormWorry.getType() == 0) {
            dormWorry.setUserId(user.getId());
        } else {
            dormWorry.setBuild(user.getBuild());
        }
        Page<DormWorry> page = new Page<>(dormWorry.getPageNumber(),dormWorry.getPageSize());
        QueryWrapper<DormWorry> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dormWorry.getUserId()),DormWorry::getUserId,dormWorry.getUserId())
                .like(StringUtils.isNotBlank(dormWorry.getUserName()),DormWorry::getUserName,dormWorry.getUserName())
                .like(StringUtils.isNotBlank(dormWorry.getBuild()),DormWorry::getBuild,dormWorry.getBuild())
                .like(StringUtils.isNotBlank(dormWorry.getRoom()),DormWorry::getRoom,dormWorry.getRoom());
        Page<DormWorry> dormWorryPage = dormWorryService.page(page, queryWrapper);
        return Result.success(dormWorryPage);
    }

    /** 根据id获取违纪 */
    @GetMapping("getDormWorryById")
    public Result getDormWorryById(@RequestParam("id")String id) {
        DormWorry dormWorry = dormWorryService.getById(id);
        return Result.success(dormWorry);
    }

    /** 保存违纪 */
    @PostMapping("saveDormWorry")
    public Result saveDormWorry(@RequestBody DormWorry dormWorry) {
        User user = userService.getById(TokenUtils.getUserIdByToken());
        dormWorry.setUserId(user.getId());
        dormWorry.setBuild(user.getBuild());
        dormWorry.setRoom(user.getRoom());
        dormWorry.setUserName(user.getUserName());
        boolean save = dormWorryService.save(dormWorry);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑违纪 */
    @PostMapping("editDormWorry")
    public Result editDormWorry(@RequestBody DormWorry dormWorry) {
        boolean save = dormWorryService.updateById(dormWorry);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除违纪 */
    @GetMapping("removeDormWorry")
    public Result removeDormWorry(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dormWorryService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("违纪id不能为空！");
        }
    }

}
