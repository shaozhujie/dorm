package com.project.dorm.controller.major;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.dorm.domain.DormMajor;
import com.project.dorm.domain.Result;
import com.project.dorm.enums.ResultCode;
import com.project.dorm.service.DormMajorService;
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
 * @description: 专业controller
 * @date 2024/03/15 09:18
 */
@Controller
@ResponseBody
@RequestMapping("major")
public class DormMajorController {

    @Autowired
    private DormMajorService dormMajorService;

    /** 分页获取专业 */
    @PostMapping("getDormMajorPage")
    public Result getDormMajorPage(@RequestBody DormMajor dormMajor) {
        Page<DormMajor> page = new Page<>(dormMajor.getPageNumber(),dormMajor.getPageSize());
        QueryWrapper<DormMajor> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dormMajor.getCollege()),DormMajor::getCollege,dormMajor.getCollege())
                .eq(StringUtils.isNotBlank(dormMajor.getMajor()),DormMajor::getMajor,dormMajor.getMajor());
        Page<DormMajor> dormMajorPage = dormMajorService.page(page, queryWrapper);
        return Result.success(dormMajorPage);
    }

    @GetMapping("getDormMajorList")
    public Result getDormMajorList() {
        List<DormMajor> majorList = dormMajorService.list();
        return Result.success(majorList);
    }

    /** 根据id获取专业 */
    @GetMapping("getDormMajorById")
    public Result getDormMajorById(@RequestParam("id")String id) {
        DormMajor dormMajor = dormMajorService.getById(id);
        return Result.success(dormMajor);
    }

    /** 保存专业 */
    @PostMapping("saveDormMajor")
    public Result saveDormMajor(@RequestBody DormMajor dormMajor) {
        boolean save = dormMajorService.save(dormMajor);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑专业 */
    @PostMapping("editDormMajor")
    public Result editDormMajor(@RequestBody DormMajor dormMajor) {
        boolean save = dormMajorService.updateById(dormMajor);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除专业 */
    @GetMapping("removeDormMajor")
    public Result removeDormMajor(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dormMajorService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("专业id不能为空！");
        }
    }

}
