package com.example.springboot.mapper;


import com.example.springboot.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Insert("insert into student (id,password,name,phone,email,address,avatar) " +
            "values (#{id}, #{password}, #{name}, #{phone}, #{email}, #{address}, #{avatar})")
    void insert(Student student);

    @Update("update student set username = #{username} , password = #{password} ," +
            "name = #{name} ,phone = #{phone} , email = #{email} , address = #{address} , avatar = #{avatar}" +
            "where id = #{id}")
    void update(Student student);

    @Delete("delete from Student where id = #{id}")
    void delete(String id);

    @Select("select * from student")
    List<Student> selectAll();

    @Select("select * from student where id = #{id}")
    Student selectById(String id);
}
