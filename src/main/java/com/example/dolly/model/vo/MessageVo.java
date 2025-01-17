package com.example.dolly.model.vo;

import com.example.dolly.model.enums.MessageStatus;
import com.example.dolly.model.enums.MessageType;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;

public interface MessageVo {
    @Value("#{target.id.seq}")
    long getSeq();
    String getSender();
    String getContent();
    Instant getSendTime();
    MessageType getType();
    MessageStatus getStatus();
}
