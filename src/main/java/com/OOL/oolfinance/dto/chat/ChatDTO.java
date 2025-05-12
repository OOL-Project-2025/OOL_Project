package com.OOL.oolfinance.dto.chat;

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
public class ChatDTO {
    String memberId;
    String nickname;
    String message;
    LocalDateTime sendTime;
}
