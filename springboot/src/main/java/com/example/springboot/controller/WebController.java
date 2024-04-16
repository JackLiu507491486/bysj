package com.example.springboot.controller;

import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：提供接口返回数据
 * 作者：jackLiu
 * 日期：2024/4/16 15:04
 */
@RestController
@RequestMapping("/web") //一级路由
public class WebController {

    /**
     * Get前端向后端获取数据
     * @param name
     * @return
     */
    @GetMapping("/hello")   //二级路由
    public Result hello(String name){
        return Result.success(name);
    }

    /**
     * post 前端向后端传输数据，数据新增
     * RequestBody 传输json参数
     * 没有 传输 url参数
     * @param obj
     * @return
     */
    @PostMapping("/post")
    public Result post(@RequestBody Obj obj){
        return Result.success(obj);
    }

    /**
     * Put 前端向后端传输数据 数据更改
     * @param obj
     * @return
     */
    @PutMapping("/put")
    public Result put(@RequestBody Obj obj){
        return Result.success(obj);
    }

    /**
     * Delete 前端向后端传输数据 数据删除 根据id删除
     * 事例：http://localhost:9090/web/delete/1
     * 也可以传JSON数据   可以批量删除数据
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(id);
    }


}
