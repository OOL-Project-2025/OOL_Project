package com.OOL.oolfinance.entity.stock;

import jakarta.persistence.*;
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
 * @name : StockInfo
 * @date : 2/20/25 / 4:22 PM
 * @modifyed : $
 **/

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal per;

    @Column
    private BigDecimal pbr;

    @Column
    private BigDecimal roe;

    @Column
    private BigDecimal debtToEquityRatio;
    // 부채비율

    @Column
    private BigDecimal currentRatio;
    // 유동비율

    @Column
    private BigDecimal interestCoverageRatio;
    // 이자보상비율

    @Column
    private BigDecimal totalDebt;
    // 총 부채

    @Column
    private BigDecimal totalEquity;
    // 총 자본

    @Column
    private BigDecimal operatingIncome;
    // 영업 이익

    @Column
    private BigDecimal revenue;
    // 매출액

    @Column
    private BigDecimal netIncome;
    // 당기순이익

    @Column
    private LocalDate dividendDate;
    // 배당일자

    @Column
    private BigDecimal dividendPerShare;
    // 주당배당금

    @Column
    private BigDecimal dividendYield;
    // 배당수익률

    @Column
    private LocalDate date;
    // 데이터 기준일

    @ManyToOne
    @JoinColumn(name = "stockCode", referencedColumnName = "stockCode")
    private Stock stock;  // 종목 정보 참조
}
