package com.OOL.oolfinance.repository.chart;

import com.OOL.oolfinance.entity.chart.Chart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChartRepository
 * @date : 2025. 4. 9. / 오후 2:38
 * @modifyed :
 **/

@Repository
public interface ChartRepository extends JpaRepository<Chart, Long>, ChartCustomRepository {

    boolean existsByStockCodeAndChartDateTime(String stockCode, LocalDateTime chartDateTime);
}
