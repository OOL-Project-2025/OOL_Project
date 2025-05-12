package com.OOL.oolfinance.controller.chat;

import com.OOL.oolfinance.dto.chat.ChatDTO;
import com.OOL.oolfinance.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

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
    @SendTo("/sub/chat")
    public ChatDTO sendMessage(ChatDTO request) {

        chatService.chatSave(request);

        return new ChatDTO(request.getMemberId(), request.getNickname(), request.getMessage(), request.getSendTime());
    }
}
