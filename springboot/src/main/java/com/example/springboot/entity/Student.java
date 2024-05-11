package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 功能：用户类
 * 作者：jackLiu
 * 日期：2024/4/16 21:50
 */

@Getter
@Setter
@TableName("student")
public class Student {
    private String id;
    private String name;
    private String password;
    private String phone;
    private String email;
    private String address;
    private String avatar;

    @TableField(exist = false)
    private String token;
}
