package com.OOL.oolfinance.dto.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockTableDTO
 * @date : 3/26/25 / 11:47 PM
 * @modifyed : $
 **/

@Getter
@Builder
@AllArgsConstructor
public class StockTableDTO {
    String stockSymbol;

    String stockName;

    BigDecimal previousClose;

    BigDecimal currentClose;

    BigDecimal tradingValue;

    long tradingVolume;
}
