package com.example.springboot.UserService;

import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.ServletException;
import java.util.List;

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

    public void updateUser(User user) {
        userMapper.update(user);
    }

    public void deleteUser(Integer id) {
        userMapper.delete(id);
    }

    public void batchDeleteUser(List<Integer> ids) {
        for (Integer id : ids) {
            userMapper.delete(id);
        }
    }

    public List<User> selectAllUser() {
        return userMapper.selectAll();
    }

    public User selectByIdUser(Integer id) {
        return userMapper.selectById(id);
    }

    //验证用户账户合法
    public User login(User user) {
        //根据用户名查询数据库用户信息
        User dbuser = userMapper.selectById(user.getId());
        if(dbuser == null) {
            //抛出异常
            throw new ServiceException("用户名或密码错误");
        }
        if(!user.getPassword().equals(dbuser.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        return dbuser;
    }
}
