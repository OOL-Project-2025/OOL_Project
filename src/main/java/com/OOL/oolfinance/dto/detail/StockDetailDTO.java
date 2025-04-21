package com.OOL.oolfinance.dto.detail;

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
public class StockDetailDTO {
    private BigDecimal per;

    private BigDecimal pbr;

    private BigDecimal roe;

    private BigDecimal debtToEquityRatio;
    // 부채비율

    private BigDecimal currentRatio;
    // 유동비율

    private BigDecimal interestCoverageRatio;
    // 이자보상비율

    private BigDecimal totalDebt;
    // 총 부채

    private BigDecimal totalEquity;
    // 총 자본

    private BigDecimal operatingIncome;
    // 영업 이익

    private BigDecimal revenue;
    // 매출액

    private BigDecimal netIncome;
    // 당기순이익

    private LocalDate dividendDate;
    // 배당일자

    private BigDecimal dividendPerShare;
    // 주당배당금

    private BigDecimal dividendYield;
    // 배당수익률

    private LocalDate date;
    // 데이터 기준일
}
