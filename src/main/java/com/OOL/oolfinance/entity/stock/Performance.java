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
 * @name : Performance
 * @date : 2/20/25 / 3:50 PM
 * @modifyed : $
 **/

@Entity
@Getter
@NoArgsConstructor
public class Performance {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private LocalDate earningsDate;

    @Column
    private BigDecimal estimatedEps;

    @Column
    private BigDecimal estimatedNetRevenue;

    @ManyToOne
    @JoinColumn(name = "stock_code", referencedColumnName = "stockCode")
    private Stock stock;  // 종목 정보 참조
}
