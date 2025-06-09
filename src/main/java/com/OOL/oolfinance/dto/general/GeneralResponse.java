package com.OOL.oolfinance.dto.general;

import com.OOL.oolfinance.enums.StatusEnum;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : GeneralResponse
 * @date : 2025. 5. 30. / 오전 1:37
 * @modifyed :
 **/

@Data
@Builder
public class GeneralResponse<T> {

    private StatusEnum status;

    private String message;

    private T data;
}
