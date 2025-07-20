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
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping(value = "/api/main")
@RestController
@RequiredArgsConstructor
@Tag(name = "Main", description = "메인 페이지 관련 API")
public class MainRestController {
    private final MarketIndexService marketIndexService;
    private final StockService stockService;

    @GetMapping(value = "/indices")
    @Operation(summary = "지수 리스트 조회", description = "메인 페이지에서 지수 종류에 따른 목록을 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "fail", content = @Content(mediaType = "application/json"))
    })
    @Parameters(value = {
            @Parameter(name = "type", description = "지수 종류", example = "STOCK_INDEX")
    })
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

    @GetMapping(value = "/stocks")
    @Operation(summary = "주식 리스트 조회", description = "메인 페이지에서 정렬기준에 따른 주식 리스트를 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "fail", content = @Content(mediaType = "application/json"))
    })
    @Parameters(value = {
            @Parameter(name = "page", description = "페이지네이션 페이지", example = "0"),
            @Parameter(name = "country", description = "국내장, 미국장", example = "KR"),
            @Parameter(name = "sort", description = "정렬 기준", example = "TRADING_VOLUME")
    })
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
