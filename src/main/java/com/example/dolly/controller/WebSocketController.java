package com.example.dolly.controller;

import com.example.dolly.model.dto.MsgDto;
import com.example.dolly.model.entity.MessageEntity;
import com.example.dolly.model.vo.MessageVo;
import com.example.dolly.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    // FIXME:對方若不在線上就不用發送了
    @MessageMapping("/send")
    public void privateMessage(MsgDto dto, Principal principal) throws InterruptedException {
//        Thread.sleep(1000);
        // TODO: 寫入資料庫
        MessageVo messageVo = chatService.sendMessage(principal.getName(),dto);

        this.messagingTemplate.convertAndSendToUser(dto.receiverId(), "/queue/private", messageVo);
        // TODO: 回應發送人時間、訊息ID
        this.messagingTemplate.convertAndSendToUser(principal.getName(),
                "/queue/return",
                messageVo,
                Map.of("uuid",dto.sendId())
        );
    }
}
