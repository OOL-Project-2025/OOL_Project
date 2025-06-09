package com.OOL.oolfinance.dto.chart;

import com.OOL.oolfinance.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexChartResponse
 * @date : 2025. 6. 1. / 오후 10:49
 * @modifyed :
 **/
@Data
@Builder
public class IndexChartResponse {
    private StatusEnum status;

    private String message;

    private List<IndexChartDTO> data;
}
