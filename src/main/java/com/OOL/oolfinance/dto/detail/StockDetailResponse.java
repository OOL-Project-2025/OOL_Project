package com.OOL.oolfinance.dto.detail;

import com.OOL.oolfinance.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockDetailResponse
 * @date : 2025. 6. 1. / 오후 10:52
 * @modifyed :
 **/

@Data
@Builder
@Schema(name = "StockDetailResponse", description = "주식 상세보기 전용 Response")
public class StockDetailResponse {
    @Schema(name = "status", type = "StatusEnum", description = "HTTP 상태 코드")
    private StatusEnum status;

    @Schema(name = "message", type = "String", description = "상태 메시지")
    private String message;

    @Schema(name = "data", type = "List<StockDetailDTO>", description = "주식 상세내용 리스트")
    private List<StockDetailDTO> data;
}
