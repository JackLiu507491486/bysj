package com.example.springboot.controller;

/**
 * 功能：
 * 作者：jackLiu
 * 日期：2024/4/30 下午4:23
 */


import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot.UserService.ChatService;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.common.WenXinConfig;
import com.example.springboot.entity.ChatMessage;
import com.example.springboot.entity.Student;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class ChatController {

    @Resource
    private WenXinConfig wenXinConfig;

    //历史对话，需要按照user,assistant
    List<Map<String,String>> messages = new ArrayList<>();

    @Autowired
    ChatService chatService;


    ChatMessage chatMessage = new ChatMessage();



    /**
     * 查询全部用户信息
     * @return 要返回数据
     */
    @AuthAccess
    @GetMapping("/getMessage/{ID}")
    public Result selectByID(@PathVariable String ID) {
        List<ChatMessage> chatMessageList = chatService.getChatList(ID);
        return Result.success(chatMessageList);
    }

    /**
     * 删除id用户的记录
     * @param id  用户id
     * @return
     */
    @AuthAccess
    @DeleteMapping("/deleteChat/{id}")
    public Result deleteByID(@PathVariable String id) {
        chatService.deleteChat(id);
        return Result.success();
    }


    @AuthAccess
    @PostMapping("/chatID")
    public Result getChat(@org.springframework.web.bind.annotation.RequestBody String ID){
        chatMessage.setId(ID);
        return Result.success(ID);
    }



    /**
     * 获得回答，将问答储存到数据库
     * @param question 向前端接收用户的问题
     * @return
     * @throws IOException
     */
    @AuthAccess
    @PostMapping("/chat")
    public Result test1(@org.springframework.web.bind.annotation.RequestBody String question) throws IOException {
        if(question == null || question.equals("")){
            return Result.error("请输入问题");
        }
        String responseJson = null;
        //先获取令牌然后才能访问api
        if (wenXinConfig.flushAccessToken() != null) {
            HashMap<String, String> user = new HashMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            user.put("role","user");
            user.put("content",question);
            messages.add(user);
            chatMessage.setRole("user");
            chatMessage.setMessage(question);
            chatMessage.setTime(sdf.format(new Date()));
            chatService.insertChat(chatMessage);
            String requestJson = constructRequestJson(1,0.95,0.8,1.0,false,messages);
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestJson);
            Request request = new Request.Builder()
                    .url(wenXinConfig.ERNIE_Bot_4_0_URL + "?access_token=" + wenXinConfig.flushAccessToken())
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS)
                    .readTimeout(60000, TimeUnit.MILLISECONDS).build();
            try {
                responseJson = HTTP_CLIENT.newCall(request).execute().body().string();
                //将回复的内容转为一个JSONObject
                JSONObject responseObject = JSON.parseObject(responseJson);
                //将回复的内容添加到消息中
                HashMap<String, String> assistant = new HashMap<>();
                assistant.put("role","assistant");
                assistant.put("content",responseObject.getString("result"));
                chatMessage.setRole("assistant");
                chatMessage.setMessage(responseObject.getString("result"));
                chatMessage.setTime(sdf.format(new Date()));
                chatService.insertChat(chatMessage);
                messages.add(assistant);
            } catch (IOException e) {
                return Result.error("网络有问题，请稍后重试");
            }
        }
        return Result.success(responseJson);
    }

    /**
     * 构造请求的请求参数
     * @param userId
     * @param temperature
     * @param topP
     * @param penaltyScore
     * @param messages
     * @return
     */
    public String constructRequestJson(Integer userId,
                                       Double temperature,
                                       Double topP,
                                       Double penaltyScore,
                                       boolean stream,
                                       List<Map<String, String>> messages) {
        Map<String,Object> request = new HashMap<>();
        request.put("user_id",userId.toString());
        request.put("temperature",temperature);
        request.put("top_p",topP);
        request.put("penalty_score",penaltyScore);
        request.put("stream",stream);
        request.put("messages",messages);
        return JSON.toJSONString(request);
    }
}