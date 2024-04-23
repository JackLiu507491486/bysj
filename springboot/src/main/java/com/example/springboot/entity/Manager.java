package com.example.springboot.entity;

import lombok.Data;

/**
 * 功能：
 * 作者：jackLiu
 * 日期：2024/4/22 下午10:02
 */
@Data
public class Manager {
    private String id;
    private String name;
    private String password;
    private String token;
}
