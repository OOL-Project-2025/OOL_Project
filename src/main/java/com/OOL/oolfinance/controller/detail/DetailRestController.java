package com.OOL.oolfinance.controller.detail;

import com.OOL.oolfinance.dto.chat.ChatHistoryDTO;
import com.OOL.oolfinance.dto.chat.ChatHistoryResponse;
import com.OOL.oolfinance.dto.detail.StockDetailDTO;
import com.OOL.oolfinance.dto.detail.StockDetailResponse;
import com.OOL.oolfinance.enums.StatusEnum;
import com.OOL.oolfinance.service.stock.StockInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RestController
@RequiredArgsConstructor
public class DetailRestController {
    private final StockInfoService stockInfoService;

    @GetMapping(value = "/api/detail/{code}")
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
