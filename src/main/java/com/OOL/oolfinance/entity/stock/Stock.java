package com.OOL.oolfinance.entity.stock;

//import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockValuation
 * @date : 2/20/25 / 2:41 PM
 * @modifyed : $
 **/

@Entity
@Getter
@NoArgsConstructor
public class Stock {
    @Id
    private String stockCode;

    @Column
    private String stockSymbol;

    @Column
    private String stockName;

    @Column
    private BigDecimal previousClose;

    @Column
    private BigDecimal currentClose;

    @Column
    private BigDecimal tradingValue;

    @Column
    private long stockVolume;
}
