package com.project.dorm.controller.build;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.dorm.domain.DormBuild;
import com.project.dorm.domain.Result;
import com.project.dorm.enums.ResultCode;
import com.project.dorm.service.DormBuildService;
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
 * @description: 楼栋controller
 * @date 2024/03/15 08:26
 */
@Controller
@ResponseBody
@RequestMapping("build")
public class DormBuildController {

    @Autowired
    private DormBuildService dormBuildService;

    /** 分页获取楼栋 */
    @PostMapping("getDormBuildPage")
    public Result getDormBuildPage(@RequestBody DormBuild dormBuild) {
        Page<DormBuild> page = new Page<>(dormBuild.getPageNumber(),dormBuild.getPageSize());
        QueryWrapper<DormBuild> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dormBuild.getName()),DormBuild::getName,dormBuild.getName());
        Page<DormBuild> dormBuildPage = dormBuildService.page(page, queryWrapper);
        return Result.success(dormBuildPage);
    }

    @GetMapping("getDormBuildList")
    public Result getDormBuildList() {
        List<DormBuild> buildList = dormBuildService.list();
        return Result.success(buildList);
    }

    /** 根据id获取楼栋 */
    @GetMapping("getDormBuildById")
    public Result getDormBuildById(@RequestParam("id")String id) {
        DormBuild dormBuild = dormBuildService.getById(id);
        return Result.success(dormBuild);
    }

    /** 保存楼栋 */
    @PostMapping("saveDormBuild")
    public Result saveDormBuild(@RequestBody DormBuild dormBuild) {
        boolean save = dormBuildService.save(dormBuild);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑楼栋 */
    @PostMapping("editDormBuild")
    public Result editDormBuild(@RequestBody DormBuild dormBuild) {
        boolean save = dormBuildService.updateById(dormBuild);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除楼栋 */
    @GetMapping("removeDormBuild")
    public Result removeDormBuild(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dormBuildService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("楼栋id不能为空！");
        }
    }

}
