package com.OOL.oolfinance.dto.chat;

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
public class ChatHistoryDTO {
    long chatId;
    String memberId;
    String nickname;
    String message;
    LocalDateTime sendTime;
}
