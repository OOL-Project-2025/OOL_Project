package com.OOL.oolfinance.service.chart;

import com.OOL.oolfinance.dto.chart.ChartDTO;
import com.OOL.oolfinance.entity.chart.Chart;
import com.OOL.oolfinance.repository.chart.ChartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChartServiceImpl
 * @date : 2025. 4. 9. / 오후 2:48
 * @modifyed :
 **/

@Service
@RequiredArgsConstructor
public class ChartServiceImpl implements ChartService {

    private final ChartRepository chartRepository;

    @Override
    public List<ChartDTO> getChartDataAll(String stockCode) {
        return chartRepository.findByStockCode(stockCode);
    }

    @Override
    public void chartSave(Chart chartData) {
        chartRepository.save(chartData);
    }
}
