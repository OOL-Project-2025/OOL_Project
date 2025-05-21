package com.OOL.oolfinance.service.chat;

import com.OOL.oolfinance.dto.chat.ChatDTO;
import com.OOL.oolfinance.dto.chat.ChatHistoryDTO;
import com.OOL.oolfinance.entity.chat.Chat;
import com.OOL.oolfinance.repository.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    @Override
    public List<ChatHistoryDTO> getPreviousChat(long chatId) {
        Pageable pageable = PageRequest.of(0, 20);

        List<ChatHistoryDTO> chatHistoryDTOList = chatRepository.findByChatIdLessThanOrderByChatIdDesc(chatId, pageable);
        Collections.reverse(chatHistoryDTOList);

        return chatHistoryDTOList;
    }

}
