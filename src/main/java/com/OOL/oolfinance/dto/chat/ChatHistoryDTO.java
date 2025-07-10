package com.OOL.oolfinance.dto.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChatHistoryDTO
 * @date : 2025. 5. 21. / 오후 11:53
 * @modifyed :
 **/

@Getter
@Builder
@AllArgsConstructor
@ToString
@Schema(name = "ChatHistoryDTO", description = "이전 채팅 DTO")
public class ChatHistoryDTO {

    @Schema(name = "chatId", type = "long", description = "채팅 id")
    private long chatId;

    @Schema(name = "memberId", type = "String", description = "유저 id")
    private String memberId;

    @Schema(name = "nickname", type = "String", description = "닉네임")
    private String nickname;

    @Schema(name = "message", type = "String", description = "메시지 내용")
    private String message;

    @Schema(name = "sendTime", type = "LocalDateTime", description = "보낸 시간")
    private LocalDateTime sendTime;
}
