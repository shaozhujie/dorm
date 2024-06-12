package com.project.dorm.controller.college;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.dorm.domain.DormCollege;
import com.project.dorm.domain.Result;
import com.project.dorm.enums.ResultCode;
import com.project.dorm.service.DormCollegeService;
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
 * @description: 学院controller
 * @date 2024/03/15 09:10
 */
@Controller
@ResponseBody
@RequestMapping("college")
public class DormCollegeController {

    @Autowired
    private DormCollegeService dormCollegeService;

    /** 分页获取学院 */
    @PostMapping("getDormCollegePage")
    public Result getDormCollegePage(@RequestBody DormCollege dormCollege) {
        Page<DormCollege> page = new Page<>(dormCollege.getPageNumber(),dormCollege.getPageSize());
        QueryWrapper<DormCollege> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dormCollege.getName()),DormCollege::getName,dormCollege.getName());
        Page<DormCollege> dormCollegePage = dormCollegeService.page(page, queryWrapper);
        return Result.success(dormCollegePage);
    }

    @GetMapping("getDormCollegeList")
    public Result getDormCollegeList() {
        List<DormCollege> collegeList = dormCollegeService.list();
        return Result.success(collegeList);
    }

    /** 根据id获取学院 */
    @GetMapping("getDormCollegeById")
    public Result getDormCollegeById(@RequestParam("id")String id) {
        DormCollege dormCollege = dormCollegeService.getById(id);
        return Result.success(dormCollege);
    }

    /** 保存学院 */
    @PostMapping("saveDormCollege")
    public Result saveDormCollege(@RequestBody DormCollege dormCollege) {
        boolean save = dormCollegeService.save(dormCollege);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑学院 */
    @PostMapping("editDormCollege")
    public Result editDormCollege(@RequestBody DormCollege dormCollege) {
        boolean save = dormCollegeService.updateById(dormCollege);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除学院 */
    @GetMapping("removeDormCollege")
    public Result removeDormCollege(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dormCollegeService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("学院id不能为空！");
        }
    }

}
