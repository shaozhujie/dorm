package com.project.dorm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.dorm.domain.DormRoom;
import com.project.dorm.mapper.DormRoomMapper;
import com.project.dorm.service.DormRoomService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 房间service实现类
 * @date 2024/03/15 08:35
 */
@Service
public class DormRoomServiceImpl extends ServiceImpl<DormRoomMapper, DormRoom> implements DormRoomService {
}
