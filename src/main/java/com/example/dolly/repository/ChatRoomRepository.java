package com.example.dolly.repository;

import com.example.dolly.model.entity.ChatRoomEntity;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, UUID> {
    Optional<ChatRoomEntity> findByUser1AndUser2(String user1, String user2);
    <T>Slice<T> findByUser1OrUser2OrderByLastTimeDesc(String user1, String user2,Class<T> type);
}
