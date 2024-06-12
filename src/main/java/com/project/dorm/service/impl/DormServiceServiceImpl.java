package com.project.dorm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.dorm.domain.DormService;
import com.project.dorm.mapper.DormServiceMapper;
import com.project.dorm.service.DormServiceService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 维修service实现类
 * @date 2024/03/16 08:36
 */
@Service
public class DormServiceServiceImpl extends ServiceImpl<DormServiceMapper, DormService> implements DormServiceService {
}
