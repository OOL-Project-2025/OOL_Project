package com.OOL.oolfinance.entity.stock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

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
    // PER(배) 0

    @Column
    private BigDecimal pbr;
    // PBR(배) 0

    @Column
    private BigDecimal roe;
    // ROE(%) 0

    @Column
    private BigDecimal debtToEquityRatio;
    // 부채비율 0

    @Column
    private BigDecimal currentRatio;
    // 유동비율 0

    @Column
    private BigDecimal interestCoverageRatio;
    // 이자보상배율 0

    @Column
    private BigDecimal totalDebt;
    // 총 부채 0

    @Column
    private BigDecimal totalEquity;
    // 총 자본 0

    @Column
    private BigDecimal operatingIncome;
    // 영업 이익 0

    @Column
    private BigDecimal revenue;
    // 매출액 0

    @Column
    private BigDecimal netIncome;
    // 당기순이익 0

    @Column
    private String dividendDate;
    // 배당일자 x

    @Column
    private BigDecimal dividendPerShare;
    // 현금DPS(원) 0

    @Column
    private BigDecimal dividendYield;
    // 현금배당수익률 0

    @Column
    private String earningsDate;
    // 데이터 기준일 0

    @ManyToOne
    @JoinColumn(name = "stock_code", referencedColumnName = "stockCode")
    private Stock stock;  // 종목 정보 참조
}
