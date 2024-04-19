package com.example.springboot.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.springboot.entity.Student;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.StudentMapper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * 功能：JWT拦截器
 * 作者：jackLiu
 * 日期：2024/4/19 上午7:16
 */
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private StudentMapper studentMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        String token = request.getHeader("token");  // 前端header里面传过来的参数
        if(StrUtil.isBlank(token)){
            token = request.getParameter("token");  //URL参数 ？token=XXX
        }

        if(handler instanceof HandlerMethod){
            Annotation annotation = ((HandlerMethod)handler).getMethodAnnotation(AuthAccess.class);
            if(annotation != null){
                return true;
            }
        }


        if (StrUtil.isBlank(token)) {
            throw new ServiceException("401","请登录");
        }
        //获取tokenID
        String id;
        try{
            id = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException e){
            throw new ServiceException("401","请登录");
        }
        //根据token的ID查询数据库
        Student student = studentMapper.selectById(id);
        if (student == null) {
            throw new ServiceException("401","请登录");
        }
        //用户密码加签证验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(student.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            throw new ServiceException("401","请登录");
        }
        return true;
    }

}
