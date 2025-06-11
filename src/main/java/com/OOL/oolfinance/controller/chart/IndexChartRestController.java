package com.OOL.oolfinance.controller.chart;

import com.OOL.oolfinance.dto.chart.ChartResponse;
import com.OOL.oolfinance.dto.chart.IndexChartDTO;
import com.OOL.oolfinance.dto.chart.IndexChartResponse;
import com.OOL.oolfinance.enums.StatusEnum;
import com.OOL.oolfinance.service.chart.IndexChartService;
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
 * @name : IndexChartRestController
 * @date : 2025. 4. 9. / 오후 2:51
 * @modifyed :
 **/

@Slf4j
@RestController
@RequiredArgsConstructor
public class IndexChartRestController {
    private final IndexChartService indexChartService;

    @GetMapping(value = "/api/indexChart/{indexCode}")
    public ResponseEntity<IndexChartResponse> getIndexChartData(@PathVariable(value = "indexCode") String indexCode) {
        log.info("지수 차트 조회");
        List<IndexChartDTO> data = indexChartService.getIndexChartDataAll(indexCode);
        IndexChartResponse response;
        if (data.isEmpty()) {
            response = IndexChartResponse.builder()
                    .status(StatusEnum.BAD_REQUEST)
                    .message("fail")
                    .data(null)
                    .build();
        } else {
            response = IndexChartResponse.builder()
                    .status(StatusEnum.OK)
                    .message("success")
                    .data(data)
                    .build();
        }
        return ResponseEntity.ok(response);
    }
}
