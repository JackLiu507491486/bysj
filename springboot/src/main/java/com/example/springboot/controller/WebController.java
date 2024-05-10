package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.UserService.ManagerService;
import com.example.springboot.UserService.StudentService;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Manager;
import com.example.springboot.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：登录界面提供接口返回数据
 * 作者：jackLiu
 * 日期：2024/4/16 15:04
 */
@RestController
public class WebController {

    @Autowired
    StudentService studentService;
    @Autowired
    ManagerService managerService;

    @AuthAccess
    @GetMapping("/")   //二级路由
    public Result hello(){
        return Result.success("success");
    }

    /**
     *
     * @param student 从前端传回的学生输入ID和密码
     * @return
     */
   @PostMapping("/login")
    public Result loginStudent(@RequestBody Student student){
        if(StrUtil.isBlank(student.getId()) || StrUtil.isBlank(student.getPassword())){
            return Result.error("输入数据不合法");
        }
        student = studentService.login(student);
        return Result.success(student);
   }

    /**
     *
     * @param manager 从前端传回的管理员输入ID和密码
     * @return
     */
    @AuthAccess
    @PostMapping("/loginManager")
    public Result loginStudent(@RequestBody Manager manager){
        if(StrUtil.isBlank(manager.getId()) || StrUtil.isBlank(manager.getPassword())){
            return Result.error("输入数据不合法");
        }
        manager = managerService.login(manager);
        return Result.success(manager);
    }


    /**
     *
     * @param student 从前端传回的用户输入ID和密码存入数据库中
     * @return
     */
    @AuthAccess
    @PostMapping("/register")
    public Result register(@RequestBody Student student){
        if(StrUtil.isBlank(student.getId()) || StrUtil.isBlank(student.getPassword())){
            return Result.error("输入数据不合法");
        }
        studentService.register(student);
        return Result.success(student);
    }


    /**
     * 重构密码
     * @param student 从前端传回的用户输入ID和密码存入数据库中
     * @return
     */
    @AuthAccess
    @PutMapping("/forget")
    public Result forget(@RequestBody Student student){
        studentService.resetPassword(student);
        return Result.success();
    }
}
