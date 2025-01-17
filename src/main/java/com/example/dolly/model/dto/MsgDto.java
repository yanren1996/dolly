package com.example.dolly.model.dto;

import com.example.dolly.model.enums.MessageType;

public record MsgDto(
        String sendId,
        String receiverId,
        MessageType type,
        String content
) {
}
