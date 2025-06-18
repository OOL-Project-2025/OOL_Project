package com.OOL.oolfinance.dto.stock;

import com.OOL.oolfinance.enums.CountryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockDTO
 * @date : 2025. 6. 3. / 오전 12:08
 * @modifyed :
 **/

@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Schema(name = "StockDTO", description = "주식 정보 조회 DTO")
public class StockDTO {
    @Schema(name = "stockCode", type = "String", description = "주식 코드")
    @EqualsAndHashCode.Include
    String stockCode;

    @Schema(name = "stockSymbol", type = "String", description = "주식 심볼")
    String stockSymbol;

    @Schema(name = "stockName", type = "String", description = "주식명")
    String stockName;

    @Schema(name = "previousClose", type = "BigDecimal", description = "이전 종가")
    BigDecimal previousClose;

    @Schema(name = "currentClose", type = "BigDecimal", description = "당일 종가")
    BigDecimal currentClose;

    @Schema(name = "tradingValue", type = "BigDecimal", description = "거래 대금")
    BigDecimal tradingValue;

    @Schema(name = "tradingVolume", type = "long", description = "거래량")
    long tradingVolume;

    @Schema(name = "countryStatus", type = "CountryStatus", description = "국가 코드")
    CountryStatus countryStatus;

}
