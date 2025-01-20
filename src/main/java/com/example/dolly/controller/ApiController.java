package com.example.dolly.controller;

import com.example.dolly.config.StompOnline;
import com.example.dolly.model.vo.HistoryVo;
import com.example.dolly.model.vo.UserVo;
import com.example.dolly.service.ChatService;
import com.example.dolly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    UserService userService;

    @Autowired
    ChatService chatService;

    @Autowired
    private StompOnline stompOnline;

    @GetMapping("/get-others-users")
    public List<UserVo> getOthersUsers(Authentication auth) {
        return userService.getOthersUsersList(auth.getName());
    }

    @GetMapping("/get-chat-history")
    public HistoryVo getChatHistory(@RequestParam String id, @RequestParam long lastSeq, Authentication auth) {
        return chatService.getChatHistory(auth.getName(), id, lastSeq);
    }

    @GetMapping("get-chat-room")
    public String getChatRoom(Authentication auth) {
        chatService.getAllChatRoom(auth.getName());
        return "";
    }

    @GetMapping("get-online")
    public Set<String> getOnline() {
        return stompOnline;
    }
}
