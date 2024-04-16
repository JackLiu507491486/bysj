package com.example.springboot.common;

import lombok.*;

/**
 * 功能：接口统一返回包装类
 * 作者：jackLiu
 * 日期：2024/4/16 15:13
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {

    public static final String CODE_SUCCESS ="200";
    public static final String CODE_AUTH_ERROR = "401";

    public static final String CODE_SYS_ERROR = "500";
    /**
     * 请求返回编码 200 401 404 500
     * 编码表示这次请求是成功还是失败  失败类型
     */
    private String code;
    /**
     * msg 错误详细信息
     */
    private String msg;
    /**
     * data 返回的数据
     */
    private Object data;

    public static Result success() {
        return new Result(CODE_SUCCESS, "请求成功", null);
    }
    public static Result success(Object data) {
        return new Result(CODE_SUCCESS, "请求成功", data);
    }



    public static Result error(String msg){
        return new Result(CODE_SYS_ERROR, msg, null);
    }

    public static Result error(String code,String msg){
        return new Result(code, msg, null);
    }

    public static Result error(){
        return new Result(CODE_SYS_ERROR, "系统错误", null);
    }

}
