package com.OOL.oolfinance.dto;

import com.OOL.oolfinance.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : WishlistResponse
 * @date : 2025. 6. 3. / 오전 1:34
 * @modifyed :
 **/

@Data
@Builder
@Schema(name = "WishlistResponse", description = "찜 목록 조회 전용 Response")
public class WishlistResponse {
    @Schema(name = "status", type = "StatusEnum", description = "HTTP 상태 코드")
    private StatusEnum status;

    @Schema(name = "message", type = "String", description = "상태 메시지")
    private String message;

    @Schema(name = "data", type = "List<WishlistDTO>", description = "찜 목록")
    private List<WishlistDTO> data;
}
