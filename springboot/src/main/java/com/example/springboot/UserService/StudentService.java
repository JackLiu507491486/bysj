package com.example.springboot.UserService;

import com.example.springboot.entity.Student;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：
 * 作者：jackLiu
 * 日期：2024/4/16 22:01
 */

@Service
public class StudentService {

    @Autowired
    StudentMapper studentMapper;
    public void insertUser(Student student) {
        studentMapper.insert(student);
    }

    public void updateUser(Student student) {
        studentMapper.update(student);
    }

    public void deleteUser(String id) {
        studentMapper.delete(id);
    }

    public void batchDeleteUser(List<String> ids) {
        for (String id : ids) {
            studentMapper.delete(id);
        }
    }

    public List<Student> selectAllUser() {
        return studentMapper.selectAll();
    }

    public Student selectByIdUser(String id) {
        return studentMapper.selectById(id);
    }

    //验证用户账户合法
    public Student login(Student student) {
        //根据用户名查询数据库用户信息
        Student dbstudent = studentMapper.selectById(student.getId());
        if(dbstudent == null) {
            //抛出异常，数据库中没有找到该用户
            throw new ServiceException("用户名或密码错误");
        }
        if(!student.getPassword().equals(dbstudent.getPassword())) {
            //抛出异常，用户和密码不匹配
            throw new ServiceException("用户名或密码错误");
        }
        return dbstudent;
    }
}
