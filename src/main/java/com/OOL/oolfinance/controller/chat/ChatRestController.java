package com.OOL.oolfinance.controller.chat;

import com.OOL.oolfinance.dto.chat.ChatDTO;
import com.OOL.oolfinance.dto.chat.ChatHistoryDTO;
import com.OOL.oolfinance.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChatRestController
 * @date : 2025. 5. 12. / 오후 11:00
 * @modifyed :
 **/

@RestController
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatService;

    @MessageMapping("/chat")
    @SendTo("/api/sub/chat")
    public ChatDTO sendMessage(ChatDTO request) {

        chatService.chatSave(request);

        return new ChatDTO(request.getMemberId(), request.getNickname(), request.getMessage(), request.getSendTime());
    }

    @GetMapping("/api/chat/all")
    public List<ChatHistoryDTO> getPreviousChat(@RequestParam(required = false, defaultValue = "-1", value = "chatId") long chatId) {

        return chatService.getPreviousChat(chatId);
    }
}
