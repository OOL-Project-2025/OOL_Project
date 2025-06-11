package com.OOL.oolfinance.repository.marketIndex;

import com.OOL.oolfinance.dto.main.IndexDTO;
import static com.OOL.oolfinance.entity.stock.QMarketIndex.*;
import com.OOL.oolfinance.enums.IndexStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexCustomRepositoryImpl
 * @date : 3/24/25 / 10:27 PM
 * @modifyed : $
 **/

@Repository
@RequiredArgsConstructor
public class MarketIndexCustomRepositoryImpl implements MarketIndexCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<IndexDTO> findAllIndicesByIndexStatus(IndexStatus requestStatus) {

        List<IndexDTO> indexDTOList = jpaQueryFactory
                .select(Projections.constructor(IndexDTO.class,
                        marketIndex.indexSymbol,
                        marketIndex.indexName,
                        marketIndex.previousClose,
                        marketIndex.currentClose))
                .from(marketIndex)
                .where(marketIndex.status.eq(requestStatus))
                .orderBy(marketIndex.indexName.desc())
                .fetch();

        return indexDTOList;
    }
}
