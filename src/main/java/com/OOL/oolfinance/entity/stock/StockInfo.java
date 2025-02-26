package com.OOL.oolfinance.entity.stock;

import jakarta.persistence.*;
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
@Getter
@NoArgsConstructor
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

    @Column
    private BigDecimal currentRatio;

    @Column
    private BigDecimal interestCoverageRatio;

    @Column
    private BigDecimal totalDebt;

    @Column
    private BigDecimal totalEquity;

    @Column
    private BigDecimal operatingIncome;

    @Column
    private BigDecimal revenue;

    @Column
    private BigDecimal netIncome;

    @Column
    private LocalDate dividendDate;

    @Column
    private BigDecimal dividendPerShare;

    @Column
    private BigDecimal dividendYield;

    @Column
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "stockCode", referencedColumnName = "stockCode")
    private Stock stock;  // 종목 정보 참조
}
