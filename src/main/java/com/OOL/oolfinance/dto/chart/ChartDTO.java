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
 * @name : ChartDTO
 * @date : 2025. 4. 9. / 오후 2:56
 * @modifyed :
 **/

@Builder
@Getter
@AllArgsConstructor
@Schema(name = "ChartDTO", description = "주식 차트 데이터 DTO")
public class ChartDTO {

    @Schema(name = "stockCode", type = "String", description = "주식 코드")
    private String stockCode;

    @Schema(name = "chartDateTime", type = "LocalDateTime", description = "차트 봉의 날짜")
    private LocalDateTime chartDateTime;

    @Schema(name = "chartOpeningPrice", type = "BigDecimal", description = "시가")
    private BigDecimal chartOpeningPrice;

    @Schema(name = "chartHighPrice", type = "BigDecimal", description = "고가")
    private BigDecimal chartHighPrice;

    @Schema(name = "chartLowPrice", type = "BigDecimal", description = "저가")
    private BigDecimal chartLowPrice;

    @Schema(name = "chartClosingPrice", type = "BigDecimal", description = "종가")
    private BigDecimal chartClosingPrice;
}
