package com.OOL.oolfinance.repository.chart;

import com.OOL.oolfinance.entity.chart.IndexChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexChartRepository
 * @date : 2025. 4. 9. / 오후 2:45
 * @modifyed :
 **/

@Repository
public interface IndexChartRepository extends JpaRepository<IndexChart, Long>, IndexChartCustomRepository {
    long countByIndexCode(String indexCode);
    List<IndexChart> findAllByIndexCodeAndDateTimeAfter(String indexCode, LocalDateTime dateTime);
    IndexChart findTopByIndexCodeOrderByDateTimeDesc(String indexCode);
    List<IndexChart> findTop2ByIndexCodeOrderByDateTimeDesc(String indexCode);
}
