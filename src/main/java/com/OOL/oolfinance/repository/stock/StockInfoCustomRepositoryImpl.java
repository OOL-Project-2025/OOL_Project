package com.OOL.oolfinance.repository.stock;

import com.OOL.oolfinance.dto.detail.StockDetailDTO;
import static com.OOL.oolfinance.entity.stock.QStockInfo.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockInfoCustomRepositoryImpl
 * @date : 2025. 4. 16. / 오후 9:02
 * @modifyed :
 **/

@Repository
@RequiredArgsConstructor
public class StockInfoCustomRepositoryImpl implements StockInfoCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StockDetailDTO> findAllByStock(String stockCode) {

        return jpaQueryFactory.select(Projections.constructor(StockDetailDTO.class,
                stockInfo.per,
                stockInfo.pbr,
                stockInfo.roe,
                stockInfo.debtToEquityRatio,
                stockInfo.currentRatio,
                stockInfo.interestCoverageRatio,
                stockInfo.totalDebt,
                stockInfo.totalEquity,
                stockInfo.operatingIncome,
                stockInfo.revenue,
                stockInfo.netIncome,
                stockInfo.dividendDate,
                stockInfo.dividendPerShare,
                stockInfo.dividendYield,
                stockInfo.date))
                .from(stockInfo)
                .orderBy(stockInfo.date.asc())
                .where(stockInfo.stock.stockCode.eq(stockCode))
                .fetch();
    }
}
