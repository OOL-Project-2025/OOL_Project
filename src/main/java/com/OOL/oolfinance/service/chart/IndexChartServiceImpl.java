package com.OOL.oolfinance.service.chart;

import com.OOL.oolfinance.dto.chart.IndexChartDTO;
import com.OOL.oolfinance.entity.chart.IndexChart;
import com.OOL.oolfinance.repository.chart.IndexChartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexChartServiceImpl
 * @date : 2025. 4. 9. / 오후 2:48
 * @modifyed :
 **/

@Service
@RequiredArgsConstructor
public class IndexChartServiceImpl implements IndexChartService {

    private final IndexChartRepository indexChartRepository;

    @Override
    public List<IndexChartDTO> getIndexChartDataAll(String indexCode) {
        return indexChartRepository.findByIndexCode(indexCode);
    }

    @Override
    public void indexChartSave(IndexChart indexChart) {
        indexChartRepository.save(indexChart);
    }
}
