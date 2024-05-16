package com.example.springboot.UserService;

import com.example.springboot.entity.ChatMessage;
import com.example.springboot.mapper.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：聊天服务
 * 作者：jackLiu
 * 日期：2024/5/1 上午11:32
 */
@Service
public class ChatService {
    @Autowired
    ChatMapper chatMapper;

    public void insertChat(ChatMessage chatMessage){
        chatMapper.insert(chatMessage);
        chatMapper.insertAG(chatMessage);
    }

    public List<ChatMessage> getChatList(String id) {
        return chatMapper.selectByID(id);
    }

    public void deleteChat(String id){
        chatMapper.delete(id);
    }
}
