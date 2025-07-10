package com.OOL.oolfinance.dto.main;

import com.OOL.oolfinance.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "IndexResponse", description = "메인화면용 지수 조회 전용 Response")
public class IndexResponse {
    @Schema(name = "status", type = "StatusEnum", description = "HTTP 상태 코드")
    private StatusEnum status;

    @Schema(name = "message", type = "String", description = "상태 메시지")
    private String message;

    @Schema(name = "data", type = "List<IndexDTO>", description = "지수 리스트")
    private List<IndexDTO> data;
}
