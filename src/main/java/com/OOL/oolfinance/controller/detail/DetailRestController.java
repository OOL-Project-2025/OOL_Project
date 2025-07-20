package com.OOL.oolfinance.controller.detail;

import com.OOL.oolfinance.dto.chat.ChatHistoryDTO;
import com.OOL.oolfinance.dto.chat.ChatHistoryResponse;
import com.OOL.oolfinance.dto.detail.StockDetailDTO;
import com.OOL.oolfinance.dto.detail.StockDetailResponse;
import com.OOL.oolfinance.enums.StatusEnum;
import com.OOL.oolfinance.service.stock.StockInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : DetailRestController
 * @date : 2025. 4. 16. / 오후 9:18
 * @modifyed :
 **/

@Slf4j
@RequestMapping(value = "/api/detail")
@RestController
@RequiredArgsConstructor
@Tag(name = "Stock Detail", description = "해당 주식의 상세 정보 관련 API")
public class DetailRestController {
    private final StockInfoService stockInfoService;

    @GetMapping(value = "/{code}")
    @Operation(summary = "주식 상세 정보 조회", description = "특정 주식의 상세 정보를 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "fail", content = @Content(mediaType = "application/json"))
    })
    @Parameters(value = {
            @Parameter(name = "code", description = "종목 코드", example = "11111")
    })
    public ResponseEntity<StockDetailResponse> getStockDetail(@PathVariable(value = "code") String code) {
        log.info(code + "코드 상세 내용 조회");
        List<StockDetailDTO> data = stockInfoService.getStockDetails(code);
        StockDetailResponse response;
        if (data.isEmpty()) {
            response = StockDetailResponse.builder()
                    .status(StatusEnum.BAD_REQUEST)
                    .message("fail")
                    .data(null)
                    .build();
        } else {
            response = StockDetailResponse.builder()
                    .status(StatusEnum.OK)
                    .message("success")
                    .data(data)
                    .build();
        }
        return ResponseEntity.ok(response);
    }
}
