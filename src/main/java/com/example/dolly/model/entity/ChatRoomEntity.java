package com.example.dolly.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "chat_room")
public class ChatRoomEntity {
    @Id
    @GeneratedValue
    @Column(length = 16)
    private UUID chatRoomId;

    private String user1;
    private String user2;

    @Version
    private long currentSeq;
    private String lastMessage;
    private Instant lastTime;
}
