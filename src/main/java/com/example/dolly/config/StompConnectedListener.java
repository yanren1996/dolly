package com.example.dolly.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.util.Map;

@Component
public class StompConnectedListener implements ApplicationListener<SessionConnectedEvent> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private StompOnline stompOnline;

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        stompOnline.add(event.getUser().getName());
        messagingTemplate.convertAndSend("/queue/event", event.getUser().getName(), Map.of("type", "Connected"));
    }
}