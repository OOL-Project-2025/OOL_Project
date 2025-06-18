package com.OOL.oolfinance.dto.general;

import com.OOL.oolfinance.enums.StatusEnum;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "GeneralResponse", description = "조회용 Response")
public class GeneralResponse<T> {

    @Schema(name = "status", type = "StatusEnum", description = "HTTP 상태 코드")
    private StatusEnum status;

    @Schema(name = "message", type = "String", description = "상태 메시지")
    private String message;

    @Schema(name = "data", type = "T", description = "필요하다면 넣는 데이터")
    private T data;
}
