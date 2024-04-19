package com.example.springboot.exception;

/**
 * 功能：自定义ServiceException
 * 作者：jackLiu
 * 日期：2024/4/17 下午9:38
 */
public class ServiceException extends RuntimeException{
     final private String code;

    public String getCode() {
        return code;
    }

    /**
     * 登录注册返回错误信息
     * @param message   错误信息
     */
    public ServiceException(String message) {
        super(message);
        this.code = "500";
    }

    /**
     * 权限错误信息
     * @param code 错误代码
     * @param message 错误信息
     */
    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }
}
