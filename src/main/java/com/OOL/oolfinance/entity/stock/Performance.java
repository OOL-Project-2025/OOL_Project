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
 * @name : Performance
 * @date : 2/20/25 / 3:50 PM
 * @modifyed : $
 **/

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performance {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String earningsDate;
    // 예정 시간 X

    @Column
    private BigDecimal estimatedEps;
    // 추정 배당금

    @Column
    private BigDecimal estimatedNetRevenue;
    // 추정 매출액

    @ManyToOne
    @JoinColumn(name = "stock_code", referencedColumnName = "stockCode")
    private Stock stock;  // 종목 정보 참조
}
