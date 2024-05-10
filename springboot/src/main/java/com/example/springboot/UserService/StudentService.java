package com.example.springboot.UserService;

import com.example.springboot.entity.Student;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.StudentMapper;
import com.example.springboot.utils.TokenUtils;
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
            throw new ServiceException("账号或密码错误");
        }
        if(!student.getPassword().equals(dbstudent.getPassword())) {
            //抛出异常，用户和密码不匹配
            throw new ServiceException("账号或密码错误");
        }
        //生成token
        String token = TokenUtils.genToken(dbstudent.getId(), dbstudent.getPassword());
        dbstudent.setToken(token);
        return dbstudent;
    }

    public void register(Student student) {
        //根据用户名查询数据库用户信息
        Student dbstudent = studentMapper.selectById(student.getId());
        if(dbstudent != null) {
            throw new ServiceException("用户已存在");
        }
        if (student.getId().length()<6||student.getId().length()>20) {
            throw new ServiceException("账号长度应该在6-20之间");
        }
        if (student.getPassword().length()<6||student.getPassword().length()>20) {
            throw new ServiceException("密码长度应该在6-20之间");
        }
        student.setName("未知用户");
        student.setAvatar("http://localhost:9090/file/download/logo.jpg");
        studentMapper.insert(student);
    }

    public void resetPassword(Student student) {
        Student dbStudent = studentMapper.selectById(student.getId());
        if(dbStudent == null) {
            throw new ServiceException("用户不存在");
        }
        if (!dbStudent.getName().equals(student.getName()) || !dbStudent.getEmail().equals(student.getEmail()) || !dbStudent.getPhone().equals(student.getPhone())) {
            throw new ServiceException("验证错误");
        }
        dbStudent.setPassword("123456");
        studentMapper.update(dbStudent);
    }
}
