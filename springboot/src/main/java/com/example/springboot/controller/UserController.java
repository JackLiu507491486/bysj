package com.example.springboot.controller;

import com.example.springboot.UserService.UserService;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * 功能：
 * 作者：jackLiu
 * 日期：2024/4/16 22:03
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    /**
     * 新增用户信息
     * @param user
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        try{
            userService.insertUser(user);
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                return Result.error("插入数据库错误");
            }else {
                return Result.error("系统错误");
            }
        }
        return Result.success();
    }
}
