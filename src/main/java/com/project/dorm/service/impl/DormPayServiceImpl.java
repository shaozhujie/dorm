package com.project.dorm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.dorm.domain.DormPay;
import com.project.dorm.mapper.DormPayMapper;
import com.project.dorm.service.DormPayService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 缴费service实现类
 * @date 2024/03/16 09:04
 */
@Service
public class DormPayServiceImpl extends ServiceImpl<DormPayMapper, DormPay> implements DormPayService {
}