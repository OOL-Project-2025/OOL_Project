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
 * @name : IndexChartDTO
 * @date : 2025. 4. 10. / 오후 3:08
 * @modifyed :
 **/

@Getter
@Builder
@AllArgsConstructor
public class IndexChartDTO {
    String indexCode;

    LocalDateTime chartDateTime;

    BigDecimal chartClosingPrice;
}
