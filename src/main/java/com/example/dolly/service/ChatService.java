package com.example.dolly.service;

import com.example.dolly.model.dto.MsgDto;
import com.example.dolly.model.entity.ChatRoomEntity;
import com.example.dolly.model.entity.MessageEntity;
import com.example.dolly.model.entity.MessageId;
import com.example.dolly.model.enums.MessageStatus;
import com.example.dolly.model.vo.HistoryVo;
import com.example.dolly.model.vo.MessageVo;
import com.example.dolly.repository.ChatRoomRepository;
import com.example.dolly.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private MessageRepository messageRepository;

    /**
     * 使用者發送訊息
     */
    @Transactional
    public MessageVo sendMessage(String senderId, MsgDto dto) {
        Instant timestamp = Instant.now();

        ChatRoomEntity chatRoom = getChatRoom(senderId, dto.receiverId());
        // 更新聊天室主表
        chatRoom.setCurrentSeq(chatRoom.getCurrentSeq() + 1);
        chatRoom.setLastMessage(dto.content());
        chatRoom.setLastTime(timestamp);

        // 資料庫新增訊息
        MessageId id = new MessageId(chatRoom.getChatRoomId(), chatRoom.getCurrentSeq());
        MessageEntity messageEntity = MessageEntity.builder()
                .id(id)
                .sender(senderId)
                .type(dto.type())
                .content(dto.content())
                .status(MessageStatus.UNREAD)
                .sendTime(timestamp).build();

        chatRoomRepository.save(chatRoom);
        messageRepository.save(messageEntity);
        // 這邊轉換方式有點多餘，多查詢了一次(使用object mapper轉換vo會更好)
        return messageRepository.findById(id, MessageVo.class);
    }

    /**
     * 取得5筆聊天
     *
     * @param lastSeq 用戶最舊一則訊息seq
     */
    public HistoryVo getChatHistory(String id1, String id2, long lastSeq) {
        ChatRoomEntity chatRoom = getChatRoom(id1, id2);
        Slice<MessageVo> entities = lastSeq == -1 ?
                messageRepository.findByIdChatRoomId(
                        chatRoom.getChatRoomId(),
                        Sort.by(Sort.Direction.DESC, "id.seq"),
                        Limit.of(5),
                        MessageVo.class
                )
                :
                messageRepository.findByIdChatRoomIdAndIdSeqLessThan(
                        chatRoom.getChatRoomId(),
                        lastSeq,
                        Sort.by(Sort.Direction.DESC, "id.seq"),
                        Limit.of(5),
                        MessageVo.class
                );
        return HistoryVo.builder()
                .Content(entities.getContent())
                .HasNext(entities.hasNext())
                .build();
    }

    public Slice<ChatRoomEntity> getAllChatRoom(String userId) {
        return chatRoomRepository.findByUser1OrUser2OrderByLastTimeDesc(userId, userId, ChatRoomEntity.class);
    }

    /**
     * 傳入兩個用戶ID，建立or取得-他們兩人的聊天室
     * 使用id進行排序來確定誰是user1誰是user2
     */
    private ChatRoomEntity getChatRoom(String id1, String id2) {
        String[] ids = {id1, id2};
        Arrays.sort(ids);
        Optional<ChatRoomEntity> chatRoom = chatRoomRepository.findByUser1AndUser2(ids[0], ids[1]);
        if (chatRoom.isPresent()) {
            return chatRoom.get();
        } else {
            ChatRoomEntity entity = new ChatRoomEntity();
            entity.setUser1(ids[0]);
            entity.setUser2(ids[1]);
            entity.setCurrentSeq(0L);
            return chatRoomRepository.save(entity);
        }
    }

}
