package com.example.springboot.controller;

import com.example.springboot.UserService.UserService;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 功能：实现用户信息前后端交互
 * 作者：jackLiu
 * 日期：2024/4/16 22:03
 */

@RestController
@RequestMapping("/user")
@CrossOrigin
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

    /**
     * 修改用户信息
     * @param user  用户信息
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        try{
            userService.updateUser(user);
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                return Result.error("插入数据库错误");
            }else {
                return Result.error("系统错误");
            }
        }
        return Result.success();

    }


    /**
     * 删除用户信息
     * @param id  用户id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.deleteUser(id);
        return Result.success();
    }

    /**
     * 批量删除用户信息
     * @param ids  用户们id
     * @return
     */
    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        userService.batchDeleteUser(ids);
        return Result.success();
    }

    /**
     * 查询全部用户信息
     * @return 要返回数据
     */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<User> users = userService.selectAllUser();
        return Result.success(users);
    }

    /**
     * 查询id对应用户信息
     * @return 要返回数据
     */
    @GetMapping("/selectById/{id}")
    public Result selectAll(@PathVariable Integer id) {
        User user = userService.selectByIdUser(id);
        return Result.success(user);
    }
}
