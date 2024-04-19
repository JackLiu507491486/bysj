package com.example.springboot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springboot.entity.Student;
import com.example.springboot.mapper.StudentMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 功能：Token工具类
 * 作者：jackLiu
 * 日期：2024/4/19 上午7:52
 */
@Component
public class TokenUtils {
    private static StudentMapper staticStudentMapper;

    @Resource
    StudentMapper studentMapper;

    @PostConstruct
    public void setStudentService() {
        staticStudentMapper = studentMapper;
    }

    /**
     * 生成token
     * @return
     */
    public static String genToken(String id,String sign) {
        return JWT.create().withAudience(id)//id保存token里面
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))//2h token过期
                .sign(Algorithm.HMAC256(sign));//password为token密钥
    }

    /**
     * 获取现在登录用户 利用token
     * @return
     */
    public static Student getStudent() {
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(StrUtil.isNotBlank(token)){
                String id = JWT.decode(token).getAudience().get(0);
                return staticStudentMapper.selectById(id);
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

}
