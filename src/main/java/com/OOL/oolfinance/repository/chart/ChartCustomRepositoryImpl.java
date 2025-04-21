package com.OOL.oolfinance.repository.chart;

import com.OOL.oolfinance.dto.chart.ChartDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.OOL.oolfinance.entity.chart.QChart.*;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChartCustomRepositoryImpl
 * @date : 2025. 4. 9. / 오후 2:40
 * @modifyed :
 **/

@Repository
@RequiredArgsConstructor
public class ChartCustomRepositoryImpl implements ChartCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChartDTO> findByStockCode(String stockCode) {
        List<ChartDTO> chartDatalist = jpaQueryFactory
                .select(Projections.constructor(ChartDTO.class,
                        chart.stockCode,
                        chart.chartDateTime,
                        chart.chartOpeningPrice,
                        chart.chartHighPrice,
                        chart.chartLowPrice,
                        chart.chartClosingPrice
                        ))
                .from(chart)
                .where(chart.stockCode.eq(stockCode))
                .orderBy(chart.chartDateTime.asc())
                .fetch();

        return chartDatalist;
    }
}
