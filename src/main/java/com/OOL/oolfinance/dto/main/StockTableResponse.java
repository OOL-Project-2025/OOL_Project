package com.OOL.oolfinance.dto.main;

import com.OOL.oolfinance.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockTableResponse
 * @date : 2025. 6. 1. / 오후 11:02
 * @modifyed :
 **/

@Data
@Builder
public class StockTableResponse {
    private StatusEnum status;

    private String message;

    private List<StockTableDTO> data;
}
