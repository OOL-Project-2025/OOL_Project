package com.OOL.oolfinance.repository.chart;

import com.OOL.oolfinance.dto.chart.IndexChartDTO;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexChartCustomRepository
 * @date : 2025. 4. 9. / 오후 2:45
 * @modifyed :
 **/
public interface IndexChartCustomRepository {
    List<IndexChartDTO> findByIndexCode(String indexCode);
}
