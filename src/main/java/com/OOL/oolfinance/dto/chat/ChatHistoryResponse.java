package com.OOL.oolfinance.dto.chat;

import com.OOL.oolfinance.enums.StatusEnum;
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
public class ChatHistoryResponse {
    private StatusEnum status;

    private String message;

    private List<ChatHistoryDTO> data;
}
