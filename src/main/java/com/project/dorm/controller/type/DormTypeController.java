package com.project.dorm.controller.type;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.dorm.domain.DormType;
import com.project.dorm.domain.Result;
import com.project.dorm.enums.ResultCode;
import com.project.dorm.service.DormTypeService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 缴费类型controller
 * @date 2024/03/16 08:52
 */
@Controller
@ResponseBody
@RequestMapping("type")
public class DormTypeController {

    @Autowired
    private DormTypeService dormTypeService;

    /** 分页获取缴费类型 */
    @PostMapping("getDormTypePage")
    public Result getDormTypePage(@RequestBody DormType dormType) {
        Page<DormType> page = new Page<>(dormType.getPageNumber(),dormType.getPageSize());
        QueryWrapper<DormType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dormType.getName()),DormType::getName,dormType.getName());
        Page<DormType> dormTypePage = dormTypeService.page(page, queryWrapper);
        return Result.success(dormTypePage);
    }

    @GetMapping("getDormTypeList")
    public Result getDormTypeList() {
        List<DormType> typeList = dormTypeService.list();
        return Result.success(typeList);
    }

    /** 根据id获取缴费类型 */
    @GetMapping("getDormTypeById")
    public Result getDormTypeById(@RequestParam("id")String id) {
        DormType dormType = dormTypeService.getById(id);
        return Result.success(dormType);
    }

    /** 保存缴费类型 */
    @PostMapping("saveDormType")
    public Result saveDormType(@RequestBody DormType dormType) {
        boolean save = dormTypeService.save(dormType);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑缴费类型 */
    @PostMapping("editDormType")
    public Result editDormType(@RequestBody DormType dormType) {
        boolean save = dormTypeService.updateById(dormType);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除缴费类型 */
    @GetMapping("removeDormType")
    public Result removeDormType(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dormTypeService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("缴费类型id不能为空！");
        }
    }

}
