package com.example.springboot.controller;

import com.example.springboot.UserService.ManagerService;
import com.example.springboot.UserService.StudentService;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Manager;
import com.example.springboot.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：
 * 作者：jackLiu
 * 日期：2024/5/11 下午9:39
 */
@RestController
@RequestMapping("/manger")
@CrossOrigin
public class MangerController {
    @Autowired
    ManagerService mangerService;

    @AuthAccess
    @PutMapping("/update")
    public Result update(@RequestBody Manager manager) {
        try{
            mangerService.update(manager);
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                return Result.error("插入数据库错误");
            }else {
                return Result.error("系统错误");
            }
        }
        return Result.success();
    }

    @AuthAccess
    @PostMapping("/insert")
    public Result insert(@RequestBody Manager manager) {
        try{
            mangerService.insert(manager);
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
