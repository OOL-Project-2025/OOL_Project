package com.OOL.oolfinance.dto.chat;

import com.OOL.oolfinance.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChatHistoryResponse
 * @date : 2025. 6. 1. / 오후 10:50
 * @modifyed :
 **/

@Data
@Builder
@Schema(name = "ChatHistoryResponse", description = "채팅 이전기록 전용 Response")
public class ChatHistoryResponse {

    @Schema(name = "status", type = "StatusEnum", description = "HTTP 상태 코드")
    private StatusEnum status;

    @Schema(name = "message", type = "String", description = "상태 메시지")
    private String message;

    @Schema(name = "data", type = "List<ChatHistoryDTO>", description = "채팅 이전기록 리스트")
    private List<ChatHistoryDTO> data;
}
