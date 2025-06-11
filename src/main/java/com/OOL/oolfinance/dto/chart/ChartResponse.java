package com.OOL.oolfinance.dto.chart;

import com.OOL.oolfinance.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChartResponse
 * @date : 2025. 6. 1. / 오후 10:42
 * @modifyed :
 **/

@Data
@Builder
public class ChartResponse {
    private StatusEnum status;

    private String message;

    private List<ChartDTO> data;
}
