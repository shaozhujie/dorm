package com.project.dorm.controller.room;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.dorm.domain.DormRoom;
import com.project.dorm.domain.Result;
import com.project.dorm.enums.ResultCode;
import com.project.dorm.service.DormRoomService;
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
 * @description: 房间controller
 * @date 2024/03/15 08:35
 */
@Controller
@ResponseBody
@RequestMapping("room")
public class DormRoomController {

    @Autowired
    private DormRoomService dormRoomService;

    /** 分页获取房间 */
    @PostMapping("getDormRoomPage")
    public Result getDormRoomPage(@RequestBody DormRoom dormRoom) {
        Page<DormRoom> page = new Page<>(dormRoom.getPageNumber(),dormRoom.getPageSize());
        QueryWrapper<DormRoom> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dormRoom.getBuild()),DormRoom::getBuild,dormRoom.getBuild())
                .eq(StringUtils.isNotBlank(dormRoom.getRoom()),DormRoom::getRoom,dormRoom.getRoom());
        Page<DormRoom> dormRoomPage = dormRoomService.page(page, queryWrapper);
        return Result.success(dormRoomPage);
    }

    @GetMapping("getDormRoomList")
    public Result getDormRoomList() {
        List<DormRoom> roomList = dormRoomService.list();
        return Result.success(roomList);
    }

    /** 根据id获取房间 */
    @GetMapping("getDormRoomById")
    public Result getDormRoomById(@RequestParam("id")String id) {
        DormRoom dormRoom = dormRoomService.getById(id);
        return Result.success(dormRoom);
    }

    /** 保存房间 */
    @PostMapping("saveDormRoom")
    public Result saveDormRoom(@RequestBody DormRoom dormRoom) {
        boolean save = dormRoomService.save(dormRoom);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑房间 */
    @PostMapping("editDormRoom")
    public Result editDormRoom(@RequestBody DormRoom dormRoom) {
        boolean save = dormRoomService.updateById(dormRoom);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除房间 */
    @GetMapping("removeDormRoom")
    public Result removeDormRoom(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dormRoomService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("房间id不能为空！");
        }
    }

}
