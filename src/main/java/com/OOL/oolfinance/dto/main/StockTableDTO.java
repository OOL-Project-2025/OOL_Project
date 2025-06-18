package com.OOL.oolfinance.dto.main;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockTableDTO
 * @date : 3/26/25 / 11:47 PM
 * @modifyed : $
 **/

@Getter
@Builder
@AllArgsConstructor
@Schema(name = "StockTableDTO", description = "주식 목록 DTO")
public class StockTableDTO {
    @Schema(name = "stockSymbol", type = "String", description = "주식 심볼")
    private String stockSymbol;

    @Schema(name = "stockName", type = "String", description = "주식 심볼")
    private String stockName;

    @Schema(name = "previousClose", type = "BigDecimal", description = "이전 종가")
    private BigDecimal previousClose;

    @Schema(name = "currentClose", type = "BigDecimal", description = "당일 종가")
    private BigDecimal currentClose;

    @Schema(name = "tradingValue", type = "BigDecimal", description = "거래대금")
    private BigDecimal tradingValue;

    @Schema(name = "tradingVolume", type = "long", description = "거래량")
    private long tradingVolume;
}
