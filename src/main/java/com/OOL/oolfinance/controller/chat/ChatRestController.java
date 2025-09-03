package com.OOL.oolfinance.controller.chat;

import com.OOL.oolfinance.dto.chat.ChatDTO;
import com.OOL.oolfinance.dto.chat.ChatHistoryDTO;
import com.OOL.oolfinance.dto.chat.ChatHistoryResponse;
import com.OOL.oolfinance.enums.StatusEnum;
import com.OOL.oolfinance.service.chat.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.parameters.P;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Chat", description = "채팅 관련 API")
public class ChatRestController {

    private final ChatService chatService;

    // TODO chatDTO 변경

    @MessageMapping("/chat")
    @SendTo("/api/sub/chat")
    public ChatDTO sendMessage(ChatDTO request) {

        chatService.chatSave(request);

        return new ChatDTO(request.getProvider(), request.getProviderId(), request.getNickname(), request.getMessage(), request.getSendTime());
    }

    @GetMapping("/api/chat")
    @Operation(summary = "이전 채팅 조회", description = "조회된 마지막 채팅의 id를 넣어 이전 채팅을 가져오는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "fail", content = @Content(mediaType = "application/json"))
    })
    @Parameters(value = {
            @Parameter(name = "chatId", description = "마지막 채팅 Id", example = "1")
    })
    public ResponseEntity<ChatHistoryResponse> getPreviousChat(@RequestParam(required = false, defaultValue = "-1", value = "chatId") long chatId) {
        log.info((chatId == -1 ? "최근 기록" : Long.toString(chatId)+"번 채팅") + " 이전 채팅 내용 조회");
        List<ChatHistoryDTO> data = chatService.getPreviousChat(chatId);
        ChatHistoryResponse response;
        if (data.isEmpty()) {
            response = ChatHistoryResponse.builder()
                    .status(StatusEnum.BAD_REQUEST)
                    .message("fail")
                    .data(null)
                    .build();
        } else {
            response = ChatHistoryResponse.builder()
                    .status(StatusEnum.OK)
                    .message("success")
                    .data(data)
                    .build();
        }
        return ResponseEntity.ok(response);
    }
}
