package com.project.dorm.controller.pay;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.dorm.domain.DormPay;
import com.project.dorm.domain.Result;
import com.project.dorm.domain.User;
import com.project.dorm.enums.ResultCode;
import com.project.dorm.service.DormPayService;
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
 * @description: 缴费controller
 * @date 2024/03/16 09:04
 */
@Controller
@ResponseBody
@RequestMapping("pay")
public class DormPayController {

    @Autowired
    private DormPayService dormPayService;
    @Autowired
    private UserService userService;

    /** 分页获取缴费 */
    @PostMapping("getDormPayPage")
    public Result getDormPayPage(@RequestBody DormPay dormPay) {
        User user = userService.getById(TokenUtils.getUserIdByToken());
        if (dormPay.getFlag() == 0) {
            dormPay.setUserId(user.getId());
        } else if (dormPay.getFlag() == 1) {
            dormPay.setBuild(user.getBuild());
        }
        Page<DormPay> page = new Page<>(dormPay.getPageNumber(),dormPay.getPageSize());
        QueryWrapper<DormPay> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dormPay.getType()),DormPay::getType,dormPay.getType())
                .eq(StringUtils.isNotBlank(dormPay.getUserId()),DormPay::getUserId,dormPay.getUserId())
                .like(StringUtils.isNotBlank(dormPay.getBuild()),DormPay::getBuild,dormPay.getBuild())
                .like(StringUtils.isNotBlank(dormPay.getRoom()),DormPay::getRoom,dormPay.getRoom())
                .eq(dormPay.getState() != null,DormPay::getState,dormPay.getState())
                .like(StringUtils.isNotBlank(dormPay.getCreateBy()),DormPay::getCreateBy,dormPay.getCreateBy());
        Page<DormPay> dormPayPage = dormPayService.page(page, queryWrapper);
        return Result.success(dormPayPage);
    }

    /** 根据id获取缴费 */
    @GetMapping("getDormPayById")
    public Result getDormPayById(@RequestParam("id")String id) {
        DormPay dormPay = dormPayService.getById(id);
        return Result.success(dormPay);
    }

    /** 保存缴费 */
    @PostMapping("saveDormPay")
    public Result saveDormPay(@RequestBody DormPay dormPay) {
        User user = userService.getById(TokenUtils.getUserIdByToken());
        dormPay.setBuild(user.getBuild());
        dormPay.setRoom(user.getRoom());
        dormPay.setUserId(user.getId());
        boolean save = dormPayService.save(dormPay);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑缴费 */
    @PostMapping("editDormPay")
    public Result editDormPay(@RequestBody DormPay dormPay) {
        boolean save = dormPayService.updateById(dormPay);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除缴费 */
    @GetMapping("removeDormPay")
    public Result removeDormPay(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dormPayService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("缴费id不能为空！");
        }
    }

}
