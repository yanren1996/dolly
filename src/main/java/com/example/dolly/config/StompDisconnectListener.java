package com.example.dolly.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@Component
public class StompDisconnectListener implements ApplicationListener<SessionDisconnectEvent> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private StompOnline stompOnline;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        stompOnline.remove(event.getUser().getName());
        messagingTemplate.convertAndSend("/queue/event", event.getUser().getName(), Map.of("type", "Disconnect"));
    }
}
