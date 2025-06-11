package com.OOL.oolfinance.dto.stock;

import com.OOL.oolfinance.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockResponse
 * @date : 2025. 6. 3. / 오전 12:17
 * @modifyed :
 **/

@Data
@Builder
public class StockResponse {
    private StatusEnum status;

    private String message;

    private List<StockDTO> data;
}
