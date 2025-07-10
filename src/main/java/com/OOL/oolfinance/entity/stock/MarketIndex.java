package com.OOL.oolfinance.entity.stock;

import com.OOL.oolfinance.enums.IndexStatus;
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
 * @name : Index
 * @date : 3/24/25 / 9:58 PM
 * @modifyed : $
 **/

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MarketIndex {

    @Id
    private String indexCode;

    @Column
    private String indexSymbol;

    @Column
    private String indexName;

    @Column
    private BigDecimal previousClose;

    @Column
    private BigDecimal currentClose;

    @Column
    private IndexStatus status;

    public void updateDailyData(BigDecimal previousClose, BigDecimal currentClose) {
        this.previousClose = previousClose;
        this.currentClose = currentClose;
    }
}
