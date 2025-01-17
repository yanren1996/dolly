package com.example.dolly.model.entity;

import com.example.dolly.model.enums.MessageStatus;
import com.example.dolly.model.enums.MessageType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "message")
public class MessageEntity {
    @EmbeddedId
    private MessageId id;
    private String sender;
    private String content;
    private Instant sendTime;
    private MessageType type;
    private MessageStatus status;
}
