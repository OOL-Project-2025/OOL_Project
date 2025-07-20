package com.OOL.oolfinance.dto.chart;

import com.OOL.oolfinance.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChartResponse
 * @date : 2025. 6. 1. / 오후 10:42
 * @modifyed :
 **/

@Data
@Builder
@Schema(name = "ChartResponse", description = "차트 조회 전용 Response")
public class ChartResponse {

    @Schema(name = "status", type = "StatusEnum", description = "HTTP 상태 코드")
    private StatusEnum status;

    @Schema(name = "message", type = "String", description = "상태 메시지")
    private String message;

    @Schema(name = "data", type = "List<ChartDTO>", description = "차트 봉 데이터 리스트")
    private List<ChartDTO> data;
}
