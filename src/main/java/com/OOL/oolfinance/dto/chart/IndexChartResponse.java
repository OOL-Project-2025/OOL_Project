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
 * @name : IndexChartResponse
 * @date : 2025. 6. 1. / 오후 10:49
 * @modifyed :
 **/
@Data
@Builder
@Schema(name = "IndexChartResponse", description = "지수 차트 조회 전용 API")
public class IndexChartResponse {
    @Schema(name = "status", type = "StatusEnum", description = "HTTP 상태 코드")
    private StatusEnum status;

    @Schema(name = "message", type = "String", description = "상태 메시지")
    private String message;

    @Schema(name = "data", type = "List<IndexChartDTO>", description = "차트 포인트 데이터 리스트")
    private List<IndexChartDTO> data;
}
