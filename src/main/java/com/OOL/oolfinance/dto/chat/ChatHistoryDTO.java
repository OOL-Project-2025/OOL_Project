package com.OOL.oolfinance.dto.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

    @Schema(name = "provider", type = "String", description = "유저 플랫폼")
    private String provider;

    @Schema(name = "providerId", type = "String", description = "유저 id")
    private String providerId;

    @Schema(name = "nickname", type = "String", description = "닉네임")
    private String nickname;

    @Schema(name = "message", type = "String", description = "메시지 내용")
    private String message;

    @Schema(name = "sendTime", type = "LocalDateTime", description = "보낸 시간")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime sendTime;
}
