package com.OOL.oolfinance.entity.stock;

//import jakarta.persistence.*;
import com.OOL.oolfinance.enums.CountryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private BigDecimal tradingValue; // 단위 백만

    @Column
    private BigDecimal tradingVolume;

    @Column
    private CountryStatus countryStatus;

    public void updatePriceAndVolume(BigDecimal previousClose, BigDecimal currentClose, BigDecimal tradingValue, BigDecimal tradingVolume) {
        this.previousClose = previousClose;
        this.currentClose = currentClose;
        this.tradingValue = tradingValue;
        this.tradingVolume = tradingVolume;
    }
}
