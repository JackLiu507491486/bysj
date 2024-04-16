package com.example.springboot.mapper;


import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("insert into `user` (username,password,name,phone,email,address,avatar) " +
            "values (#{username}, #{password}, #{name}, #{phone}, #{email}, #{address}, #{avatar})")
    void insert(User user);
}
