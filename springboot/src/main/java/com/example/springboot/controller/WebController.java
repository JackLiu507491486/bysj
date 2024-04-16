package com.example.springboot.controller;

import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能：提供接口返回数据
 * 作者：jackLiu
 * 日期：2024/4/16 15:04
 */
@RestController
public class WebController {

    @RequestMapping
    public Result hello(){
        return Result.success("Hello");
    }
}
