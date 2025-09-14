package com.OOL.oolfinance.repository.stock;

import com.OOL.oolfinance.dto.main.StockTableDTO;
import com.OOL.oolfinance.dto.stock.StockDTO;
import com.OOL.oolfinance.enums.CountryStatus;
import com.OOL.oolfinance.enums.StockSortType;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static com.OOL.oolfinance.entity.stock.QStock.stock;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockCustomRepositoryImpl
 * @date : 3/24/25 / 10:44 PM
 * @modifyed : $
 **/

@Repository
@RequiredArgsConstructor
public class StockCustomRepositoryImpl implements StockCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StockTableDTO> findByCountryStatus(Pageable pageable, CountryStatus countryStatus, StockSortType sortType) {
        OrderSpecifier<?> orderSpecifier;
        // 정렬 기준 선택
        switch (sortType) {
            case TRADING_VOLUME:
                orderSpecifier = new OrderSpecifier<>(Order.DESC, stock.tradingVolume, OrderSpecifier.NullHandling.NullsLast);
                break;
            case TRADING_VALUE:
                orderSpecifier = new OrderSpecifier<>(Order.DESC, stock.tradingValue, OrderSpecifier.NullHandling.NullsLast);
                break;
            case BIGGEST_GAINERS: // (현재가 - 이전가) 상승폭이 큰 종목
                NumberExpression<BigDecimal> gainers = stock.currentClose.subtract(stock.previousClose);
                orderSpecifier = new OrderSpecifier<>(Order.DESC, gainers, OrderSpecifier.NullHandling.NullsLast);
                break;
            case BIGGEST_LOSERS: // (이전가 - 현재가) 하락폭이 큰 종목
                NumberExpression<BigDecimal> losers = stock.previousClose.subtract(stock.currentClose);
                orderSpecifier = new OrderSpecifier<>(Order.DESC, losers, OrderSpecifier.NullHandling.NullsLast);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort type");
        }

        return jpaQueryFactory.select(Projections.constructor(StockTableDTO.class,
                        stock.stockCode,
                        stock.stockSymbol,
                        stock.stockName,
                        stock.previousClose,
                        stock.currentClose,
                        stock.tradingValue,
                        stock.tradingVolume))
                .from(stock)
                .where(stock.countryStatus.eq(countryStatus))
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<StockDTO> findAllStock() {
        return jpaQueryFactory.select(Projections.constructor(StockDTO.class,
                stock.stockCode,
                stock.stockSymbol,
                stock.stockName,
                stock.previousClose,
                stock.currentClose,
                stock.tradingValue,
                stock.tradingVolume,
                stock.countryStatus))
                .from(stock)
                .fetch();
    }
}
