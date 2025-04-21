package com.OOL.oolfinance.service.chart;

import com.OOL.oolfinance.dto.chart.IndexChartDTO;
import com.OOL.oolfinance.entity.chart.IndexChart;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexChartService
 * @date : 2025. 4. 9. / 오후 2:48
 * @modifyed : 
 **/public interface IndexChartService {
    List<IndexChartDTO> getIndexChartDataAll(String indexCode);

    void indexChartSave(IndexChart indexChart);
}
