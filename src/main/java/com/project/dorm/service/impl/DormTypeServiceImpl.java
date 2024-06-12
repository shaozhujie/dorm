package com.project.dorm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.dorm.domain.DormType;
import com.project.dorm.mapper.DormTypeMapper;
import com.project.dorm.service.DormTypeService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 缴费类型service实现类
 * @date 2024/03/16 08:52
 */
@Service
public class DormTypeServiceImpl extends ServiceImpl<DormTypeMapper, DormType> implements DormTypeService {
}
