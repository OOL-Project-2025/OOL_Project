package com.OOL.oolfinance.repository.chart;

import com.OOL.oolfinance.dto.chart.IndexChartDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.OOL.oolfinance.entity.chart.QIndexChart.*;
import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexChartCustomRepositoryImpl
 * @date : 2025. 4. 9. / 오후 2:45
 * @modifyed :
 **/

@Repository
@RequiredArgsConstructor
public class IndexChartCustomRepositoryImpl implements IndexChartCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<IndexChartDTO> findByIndexCode(String indexCode) {
        return jpaQueryFactory.select(Projections.constructor(IndexChartDTO.class,
                        indexChart.indexCode,
                        indexChart.dateTime,
                        indexChart.closingPrice))
                .from(indexChart)
                .where(indexChart.indexCode.eq(indexCode))
                .orderBy(indexChart.dateTime.asc())
                .fetch();
    }
}
