package com.OOL.oolfinance.dto.stock;

import com.OOL.oolfinance.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockResponse
 * @date : 2025. 6. 3. / 오전 12:17
 * @modifyed :
 **/

@Data
@Builder
@Schema(name = "StockResponse", description = "찜 목록에서 주식 리스트 조회 전용 Response")
public class StockResponse {
    @Schema(name = "status", type = "StatusEnum", description = "HTTP 상태 코드")
    private StatusEnum status;

    @Schema(name = "message", type = "String", description = "상태 메시지")
    private String message;

    @Schema(name = "data", type = "List<StockDTO>", description = "주식 리스트")
    private List<StockDTO> data;
}
