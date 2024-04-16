package com.example.springboot.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 功能：用户类
 * 作者：jackLiu
 * 日期：2024/4/16 21:50
 */

@Getter
@Setter
public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String avatar;

}
