package com.example.springboot.mapper;


import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into `user` (username,password,name,phone,email,address,avatar) " +
            "values (#{username}, #{password}, #{name}, #{phone}, #{email}, #{address}, #{avatar})")
    void insert(User user);

    @Update("update `user` set username = #{username} , password = #{password} ," +
            "name = #{name} ,phone = #{phone} , email = #{email} , address = #{address} , avatar = #{avatar}" +
            "where id = #{id}")
    void update(User user);

    @Delete("delete from `user` where id = #{id}")
    void delete(Integer id);

    @Select("select * from `user`")
    List<User> selectAll();

    @Select("select * from `user` where id = #{id}")
    User selectById(Integer id);
}
