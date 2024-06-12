package com.project.dorm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.dorm.domain.DormWorry;
import com.project.dorm.mapper.DormWorryMapper;
import com.project.dorm.service.DormWorryService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 违纪service实现类
 * @date 2024/03/16 10:18
 */
@Service
public class DormWorryServiceImpl extends ServiceImpl<DormWorryMapper, DormWorry> implements DormWorryService {
}
