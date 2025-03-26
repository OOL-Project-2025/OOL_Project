package com.OOL.oolfinance.dto.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexDTO
 * @date : 3/25/25 / 10:04 PM
 * @modifyed : $
 **/

@Getter
@Builder
@AllArgsConstructor
@ToString
public class IndexDTO {

    private String indexSymbol;

    private String indexName;

    private BigDecimal previousClose;

    private BigDecimal currentClose;

}
