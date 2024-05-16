package com.example.springboot.UserService;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class StudentService extends ServiceImpl<StudentMapper, Student> {

    @Autowired
    StudentMapper studentMapper;


    @Override
    public boolean save(Student entity) {
        if (StrUtil.isBlank(entity.getName())) {
            entity.setName("未知用户");
        }
        if (StrUtil.isBlank(entity.getPassword())) {
            entity.setPassword("123456");//默认密码
        }
        return super.save(entity);
    }

    public Student getStudentById(String id) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id); //eq => == where user id = #{id}
        //根据用户名查询数据库用户信息
        return getOne(queryWrapper);// select * from student where id = #{id}
    }

    //验证用户账户合法
    public Student login(Student student) {
        Student dbstudent = getStudentById(student.getId());
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
        Student dbstudent = getStudentById(student.getId());
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
        student.setAvatar("http://localhost:9090/file/download/1715405493092_none.jpg");
        studentMapper.insert(student);
    }

    public void resetPassword(Student student) {
        Student dbStudent = getStudentById(student.getId());
        if(dbStudent == null) {
            throw new ServiceException("用户不存在");
        }
        if ((student.getName() == null && dbStudent.getName() == null) ||
                (student.getName() != null && dbStudent.getName() != null && student.getName().equals(dbStudent.getName())) ||
                (student.getEmail() == null && dbStudent.getEmail() == null) ||
                (student.getEmail() != null && dbStudent.getEmail() != null && student.getEmail().equals(dbStudent.getEmail())) ||
                (student.getPhone() == null && dbStudent.getPhone() == null) ||
                (student.getPhone() != null && dbStudent.getPhone() != null && student.getPhone().equals(dbStudent.getPhone()))) {
            // 当属性为空或者属性相等时，验证成功
            dbStudent.setPassword("123456");
            updateById(dbStudent);
        } else {
            // 其他情况，验证失败
            throw new ServiceException("验证错误");
        }


    }
}
