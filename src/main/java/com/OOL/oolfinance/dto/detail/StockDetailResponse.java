package com.OOL.oolfinance.dto.detail;

import com.OOL.oolfinance.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockDetailResponse
 * @date : 2025. 6. 1. / 오후 10:52
 * @modifyed :
 **/

@Data
@Builder
public class StockDetailResponse {
    private StatusEnum status;

    private String message;

    private List<StockDetailDTO> data;
}
