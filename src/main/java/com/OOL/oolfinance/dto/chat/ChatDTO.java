package com.OOL.oolfinance.dto.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChatDTO
 * @date : 2025. 5. 8. / 오후 3:30
 * @modifyed :
 **/

@Getter
@Builder
@AllArgsConstructor
@ToString
@Schema(name = "ChatDTO", description = "채팅 DTO")
public class ChatDTO {

    @Schema(name = "memberId", type = "String", description = "유저 id")
    private String memberId;

    @Schema(name = "nickname", type = "String", description = "닉네임")
    private String nickname;

    @Schema(name = "message", type = "String", description = "메시지")
    private String message;

    @Schema(name = "sendTime", type = "LocalDateTime", description = "보낸 시간")
    private LocalDateTime sendTime;
}
