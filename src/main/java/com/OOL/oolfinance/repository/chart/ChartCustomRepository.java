package com.OOL.oolfinance.repository.chart;

import com.OOL.oolfinance.dto.chart.ChartDTO;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChartCustomRepository
 * @date : 2025. 4. 9. / 오후 2:40
 * @modifyed :
 **/

public interface ChartCustomRepository {
    List<ChartDTO> findByStockCode(String stockCode);
}
