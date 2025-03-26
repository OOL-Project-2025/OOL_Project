package com.OOL.oolfinance.repository.index;

import com.OOL.oolfinance.dto.index.IndexResponse;
import static com.OOL.oolfinance.entity.stock.QIndex.index;
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
public class IndexCustomRepositoryImpl implements IndexCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<IndexResponse> findAllIndicesByIndexStatus(IndexStatus requestStatus) {

        List<IndexResponse> indexResponseList = jpaQueryFactory
                .select(Projections.constructor(IndexResponse.class,
                        index.indexSymbol,
                        index.indexName,
                        index.previousClose,
                        index.currentClose))
                .from(index)
                .where(index.status.eq(requestStatus))
                .orderBy(index.indexName.desc())
                .fetch();

        return indexResponseList;
    }
}
