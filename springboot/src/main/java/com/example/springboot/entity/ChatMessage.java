package com.example.springboot.entity;

import lombok.Data;

/**
 * 功能：Chat实体类
 * 作者：jackLiu
 * 日期：2024/5/1 上午11:27
 */
@Data
public class ChatMessage {
    private String id;
    private String role;
    private String message;
    private String time;
}
