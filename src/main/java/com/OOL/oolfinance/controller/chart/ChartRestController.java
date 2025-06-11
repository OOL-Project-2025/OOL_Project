package com.OOL.oolfinance.controller.chart;

import com.OOL.oolfinance.dto.chart.ChartDTO;
import com.OOL.oolfinance.dto.chart.ChartResponse;
import com.OOL.oolfinance.enums.StatusEnum;
import com.OOL.oolfinance.service.chart.ChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChartRestController
 * @date : 2025. 4. 9. / 오후 2:50
 * @modifyed :
 **/

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChartRestController {
    private final ChartService chartService;

    @GetMapping(value = "/api/chart/{stockCode}")
    public ResponseEntity<ChartResponse> getChartData(@PathVariable(value = "stockCode") String stockCode) {
        log.info(stockCode + "코드 차트 조회");
        List<ChartDTO> data = chartService.getChartDataAll(stockCode);
        ChartResponse response;
        if (data.isEmpty()) {
            response = ChartResponse.builder()
                    .status(StatusEnum.BAD_REQUEST)
                    .message("fail")
                    .data(null)
                    .build();
        } else {
            response = ChartResponse.builder()
                    .status(StatusEnum.OK)
                    .message("success")
                    .data(data)
                    .build();
        }
        return ResponseEntity.ok(response);
    }
}
