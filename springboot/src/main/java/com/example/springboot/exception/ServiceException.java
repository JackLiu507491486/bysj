package com.example.springboot.exception;

/**
 * 功能：自定义ServiceException
 * 作者：jackLiu
 * 日期：2024/4/17 下午9:38
 */
public class ServiceException extends RuntimeException{
    public ServiceException(String message) {
        super(message);
    }
}
