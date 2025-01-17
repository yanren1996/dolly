package com.example.dolly.repository;

import com.example.dolly.model.entity.MessageEntity;
import com.example.dolly.model.entity.MessageId;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, MessageId> {
    <T> T findById(MessageId id, Class<T> type);

    /**
     * 查詢聊天室指定比sep小的
     */
    <T> Slice<T> findByIdChatRoomIdAndIdSeqLessThan(UUID chatRoomId, long seq, Sort sort, Limit limit, Class<T> type);
    <T> Slice<T> findByIdChatRoomId(UUID chatRoomId, Sort sort, Limit limit, Class<T> type);

}
