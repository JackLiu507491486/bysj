package com.example.springboot.UserService;

import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能：
 * 作者：jackLiu
 * 日期：2024/4/16 22:01
 */

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    public void insertUser(User user) {
        userMapper.insert(user);
    }
}
