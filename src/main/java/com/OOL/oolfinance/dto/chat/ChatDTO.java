package com.OOL.oolfinance.dto.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime sendTime;
}
