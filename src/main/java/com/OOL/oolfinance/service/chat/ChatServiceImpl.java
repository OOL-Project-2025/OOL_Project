package com.OOL.oolfinance.service.chat;

import com.OOL.oolfinance.dto.chat.ChatDTO;
import com.OOL.oolfinance.dto.chat.ChatHistoryDTO;
import com.OOL.oolfinance.entity.chat.Chat;
import com.OOL.oolfinance.entity.member.CustomUserDetails;
import com.OOL.oolfinance.repository.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public Chat chatSave(ChatDTO chatDTO, Principal principal) {
        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) principal;
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

        log.info("hihihi");
        log.info(chatDTO.getSendTime().toString());
        Chat chat = Chat.builder()
                .provider(user.getProvider())
                .providerId(user.getProviderId())
                .nickname(user.getUsername())
                .message(chatDTO.getMessage())
                .time(chatDTO.getSendTime())
                .build();
        log.info(chat.getTime().toString());
        chatRepository.save(chat);

        return chat;
    }

    @Override
    public List<ChatHistoryDTO> getPreviousChat(long chatId) {
        Pageable pageable = PageRequest.of(0, 20);

        List<ChatHistoryDTO> chatHistoryDTOList = chatRepository.findByChatIdLessThanOrderByChatIdDesc(chatId, pageable);
        Collections.reverse(chatHistoryDTOList);

        return chatHistoryDTOList;
    }

}
