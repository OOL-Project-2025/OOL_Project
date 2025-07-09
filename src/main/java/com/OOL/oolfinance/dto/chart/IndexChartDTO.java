package com.OOL.oolfinance.dto.chart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexChartDTO
 * @date : 2025. 4. 10. / 오후 3:08
 * @modifyed :
 **/

@Getter
@Builder
@AllArgsConstructor
@Schema(name = "IndexChartDTO", description = "지수 차트 데이터 DTO")
public class IndexChartDTO {
    @Schema(name = "indexCode", type = "String", description = "지수 코드")
    private String indexCode;

    @Schema(name = "chartDateTime", type = "LocalDateTime", description = "차트 포인트 날짜")
    private LocalDateTime chartDateTime;

    @Schema(name = "chartClosingPrice", type = "BigDecimal", description = "차트 포인트 종가")
    private BigDecimal chartClosingPrice;
}
