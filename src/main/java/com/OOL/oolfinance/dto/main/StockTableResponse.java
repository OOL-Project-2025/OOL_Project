package com.OOL.oolfinance.dto.main;

import com.OOL.oolfinance.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockTableResponse
 * @date : 2025. 6. 1. / 오후 11:02
 * @modifyed :
 **/

@Data
@Builder
@Schema(name = "StockTableResponse", description = "메인화면용 주식 조회 전용 Response")
public class StockTableResponse {
    @Schema(name = "status", type = "StatusEnum", description = "HTTP 상태 코드")
    private StatusEnum status;

    @Schema(name = "message", type = "String", description = "상태 메시지")
    private String message;

    @Schema(name = "data", type = "List<StockTableDTO>", description = "주식 리스트")
    private List<StockTableDTO> data;
}
