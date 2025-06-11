package com.OOL.oolfinance.controller.main;

import com.OOL.oolfinance.dto.main.IndexDTO;
import com.OOL.oolfinance.dto.main.IndexResponse;
import com.OOL.oolfinance.dto.main.StockTableDTO;
import com.OOL.oolfinance.dto.main.StockTableResponse;
import com.OOL.oolfinance.enums.CountryStatus;
import com.OOL.oolfinance.enums.IndexStatus;
import com.OOL.oolfinance.enums.StatusEnum;
import com.OOL.oolfinance.enums.StockSortType;
import com.OOL.oolfinance.service.marketIndex.MarketIndexService;
import com.OOL.oolfinance.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : MainRestController
 * @date : 3/26/25 / 11:44 PM
 * @modifyed : $
 **/

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainRestController {
    private final MarketIndexService marketIndexService;
    private final StockService stockService;

    @GetMapping(value = "/api/main/indices")
    public ResponseEntity<IndexResponse> getIndicesList(@RequestParam(value = "type") IndexStatus type) {
        log.info(type + "리스트 요청");

        List<IndexDTO> data = marketIndexService.fetchIndexList(type);
        IndexResponse response;

        if (data.isEmpty()) {
            response = IndexResponse.builder()
                    .status(StatusEnum.BAD_REQUEST)
                    .message("fail")
                    .data(null)
                    .build();
        } else {
            response = IndexResponse.builder()
                    .status(StatusEnum.OK)
                    .message("success")
                    .data(data)
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    // TODO: getStockPage 메서드 추가

    @GetMapping(value = "/api/main/stocks")
    public ResponseEntity<StockTableResponse> getStockList(@RequestParam(defaultValue = "0", value = "page") int page,
                                            @RequestParam(value = "country") CountryStatus status,
                                            @RequestParam(value = "sort") StockSortType sort) {
        List<StockTableDTO> data = stockService.getStockTable(page, status, sort);
        StockTableResponse response;
        if (data.isEmpty()) {
            response = StockTableResponse.builder()
                    .status(StatusEnum.BAD_REQUEST)
                    .message("fail")
                    .data(null)
                    .build();
        } else {
            response = StockTableResponse.builder()
                    .status(StatusEnum.OK)
                    .message("success")
                    .data(data)
                    .build();
        }
        return ResponseEntity.ok(response);
    }
}
