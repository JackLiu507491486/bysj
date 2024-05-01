package com.example.springboot.mapper;

import com.example.springboot.entity.ChatMessage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMapper {
    @Insert("insert into chat (id,role,message,time) " +
            "values (#{id}, #{role}, #{message},#{time})")
    void insert(ChatMessage chatMessage);

    @Delete("delete from chat where id = #{id}")
    void delete(String id);
}
