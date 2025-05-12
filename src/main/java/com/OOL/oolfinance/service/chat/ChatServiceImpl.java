package com.OOL.oolfinance.service.chat;

import com.OOL.oolfinance.dto.chat.ChatDTO;
import com.OOL.oolfinance.entity.chat.Chat;
import com.OOL.oolfinance.repository.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChatServiceImpl
 * @date : 2025. 5. 12. / 오후 11:41
 * @modifyed :
 **/

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public void chatSave(ChatDTO chatDTO) {
        Chat chat = Chat.builder()
                .memberId(chatDTO.getMemberId())
                .nickname(chatDTO.getNickname())
                .message(chatDTO.getMessage())
                .time(chatDTO.getSendTime())
                .build();

        chatRepository.save(chat);
    }
}
