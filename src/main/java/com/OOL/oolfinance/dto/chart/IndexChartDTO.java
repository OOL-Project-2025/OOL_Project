package com.OOL.oolfinance.dto.chart;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "IndexChartDTO", description = "지수 차트 데이터 DTO")
public class IndexChartDTO {
    @Schema(name = "indexCode", type = "String", description = "지수 코드")
    private String indexCode;

    @Schema(name = "chartDateTime", type = "LocalDateTime", description = "차트 포인트 날짜")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime chartDateTime;

    @Schema(name = "chartClosingPrice", type = "BigDecimal", description = "차트 포인트 종가")
    private BigDecimal chartClosingPrice;
}
