package com.OOL.oolfinance.dto.main;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "IndexDTO", description = "지수 조회 DTO")
public class IndexDTO {

    @Schema(name = "indexCode", type = "String", description = "지수 코드")
    private String indexCode;

    @Schema(name = "indexSymbol", type = "String", description = "지수 심볼")
    private String indexSymbol;

    @Schema(name = "indexName", type = "String", description = "지수명")
    private String indexName;

    @Schema(name = "previousClose", type = "BigDecimal", description = "이전 종가")
    private BigDecimal previousClose;

    @Schema(name = "currentClose", type = "BigDecimal", description = "당일 종가")
    private BigDecimal currentClose;

}
