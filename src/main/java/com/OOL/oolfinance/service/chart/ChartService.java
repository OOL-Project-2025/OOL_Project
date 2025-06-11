package com.OOL.oolfinance.service.chart;

import com.OOL.oolfinance.dto.chart.ChartDTO;
import com.OOL.oolfinance.entity.chart.Chart;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChartService
 * @date : 2025. 4. 9. / 오후 2:48
 * @modifyed : 
 **/
public interface ChartService {
    List<ChartDTO> getChartDataAll(String stockCode);

    void chartSave(Chart chartData);
}
