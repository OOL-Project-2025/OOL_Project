package com.OOL.oolfinance.controller.chart;

import com.OOL.oolfinance.dto.chart.IndexChartDTO;
import com.OOL.oolfinance.service.chart.IndexChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

@RestController
@RequiredArgsConstructor
public class IndexChartRestController {
    private final IndexChartService indexChartService;

    @GetMapping(value = "/api/indexChart")
    public List<IndexChartDTO> getIndexChartData(@RequestParam(value = "indexCode") String indexCode) {
        return indexChartService.getIndexChartDataAll(indexCode);
    }
}
