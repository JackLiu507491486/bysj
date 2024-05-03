package com.example.springboot.mapper;

import com.example.springboot.entity.ChatMessage;
import com.example.springboot.entity.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatMapper {
    @Insert("insert into chat (id,role,message,time) " +
            "values (#{id}, #{role}, #{message},#{time})")
    void insert(ChatMessage chatMessage);

    @Select("select * from chat where id = #{id}")
    List<ChatMessage> selectByID(String id);

    @Delete("delete from chat where id = #{id}")
    void delete(String id);
}
