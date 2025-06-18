package com.OOL.oolfinance.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockDetailDTO
 * @date : 2025. 4. 16. / 오후 9:05
 * @modifyed :
 **/

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "StockDetailDTO", description = "주식 상세보기 DTO")
public class StockDetailDTO {
    @Schema(name = "per", type = "BigDecimal", description = "per 주가수익비율")
    private BigDecimal per;

    @Schema(name = "pbr", type = "BigDecimal", description = "pbr 주가순자산비율")
    private BigDecimal pbr;

    @Schema(name = "roe", type = "BigDecimal", description = "roe 자기자본이익율")
    private BigDecimal roe;

    @Schema(name = "debtToEquityRatio", type = "BigDecimal", description = "부채비율")
    private BigDecimal debtToEquityRatio;
    // 부채비율

    @Schema(name = "currentRatio", type = "BigDecimal", description = "유동비율")
    private BigDecimal currentRatio;
    // 유동비율

    @Schema(name = "interestCoverageRatio", type = "BigDecimal", description = "이자보상비율")
    private BigDecimal interestCoverageRatio;
    // 이자보상비율

    @Schema(name = "totalDebt", type = "BigDecimal", description = "총 부채")
    private BigDecimal totalDebt;
    // 총 부채

    @Schema(name = "totalEquity", type = "BigDecimal", description = "총 자본")
    private BigDecimal totalEquity;
    // 총 자본

    @Schema(name = "operatingIncome", type = "BigDecimal", description = "영업 이익")
    private BigDecimal operatingIncome;
    // 영업 이익

    @Schema(name = "revenue", type = "BigDecimal", description = "매출액")
    private BigDecimal revenue;
    // 매출액

    @Schema(name = "netIncome", type = "BigDecimal", description = "당기순이익")
    private BigDecimal netIncome;
    // 당기순이익

    @Schema(name = "dividendDate", type = "LocalDate", description = "배당일자")
    private LocalDate dividendDate;
    // 배당일자

    @Schema(name = "dividendPerShare", type = "BigDecimal", description = "주식배당금")
    private BigDecimal dividendPerShare;
    // 주당배당금

    @Schema(name = "dividendYield", type = "BigDecimal", description = "배당수익률")
    private BigDecimal dividendYield;
    // 배당수익률

    @Schema(name = "date", type = "LocalDate", description = "데이터 기준일")
    private LocalDate date;
    // 데이터 기준일
}
