package com.OOL.oolfinance.dto.stock;

import com.OOL.oolfinance.enums.CountryStatus;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockDTO
 * @date : 2025. 6. 3. / 오전 12:08
 * @modifyed :
 **/

@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class StockDTO {
    @EqualsAndHashCode.Include
    String stockCode;
    String stockSymbol;
    String stockName;
    BigDecimal previousClose;
    BigDecimal currentClose;
    BigDecimal tradingValue;
    long tradingVolume;
    CountryStatus countryStatus;

}
