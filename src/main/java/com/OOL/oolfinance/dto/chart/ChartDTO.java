package com.OOL.oolfinance.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChartDTO
 * @date : 2025. 4. 9. / 오후 2:56
 * @modifyed :
 **/

@Builder
@Getter
@AllArgsConstructor
public class ChartDTO {
    String stockCode;

    LocalDateTime chartDateTime;

    BigDecimal chartOpeningPrice;

    BigDecimal chartHighPrice;

    BigDecimal chartLowPrice;

    BigDecimal chartClosingPrice;
}
