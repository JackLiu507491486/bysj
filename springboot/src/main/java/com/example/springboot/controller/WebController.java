package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.UserService.UserService;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：提供接口返回数据
 * 作者：jackLiu
 * 日期：2024/4/16 15:04
 */
@RestController
public class WebController {

    @Autowired
    UserService userService;
    /**
     * Get前端向后端获取数据
     * @return
     */
    @GetMapping("/")   //二级路由
    public Result hello(){
        return Result.success("success");
    }

   @PostMapping("/login")
    public Result login(@RequestBody User user){
        if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
            return Result.error("输入数据不合法");
        }
        user = userService.login(user);
        return Result.success(user);
   }
}
