package com.project.dorm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author AA
 * @version 1.0
 * @description: 用户表
 * @date 2024/2/26 20:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登陆账号
     */
    private String loginAccount;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String tel;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    private String build;

    private String room;

    private String college;

    private String major;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 登陆日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;

    /**
     * 修改密码日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pwdUpdateDate;

    private String remark;

    @TableField(exist = false)
    private Integer pageNumber;

    @TableField(exist = false)
    private Integer pageSize;

    @TableField(exist = false)
    private Integer sort;

    @TableField(exist = false)
    private Integer apply;

    @TableField(exist = false)
    private Integer worry;

    @TableField(exist = false)
    private Integer pay;

    @TableField(exist = false)
    private Integer service;

}
