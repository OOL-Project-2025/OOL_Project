package com.OOL.oolfinance.dto.main;

import com.OOL.oolfinance.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexResponse
 * @date : 2025. 6. 1. / 오후 10:56
 * @modifyed :
 **/

@Data
@Builder
public class IndexResponse {
    private StatusEnum status;

    private String message;

    private List<IndexDTO> data;
}
