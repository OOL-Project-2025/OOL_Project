package com.OOL.oolfinance.controller.chart;

import com.OOL.oolfinance.dto.chart.ChartDTO;
import com.OOL.oolfinance.service.chart.ChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(value = "/api/chart")
    public List<ChartDTO> getChartData(@RequestParam(value = "stockCode") String stockCode) {
        return chartService.getChartDataAll(stockCode);
    }
}
