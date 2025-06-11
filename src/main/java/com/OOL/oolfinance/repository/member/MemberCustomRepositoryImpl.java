package com.OOL.oolfinance.repository.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : MemberCustomRepositoryImpl
 * @date : 3/4/25 / 11:34 PM
 * @modifyed : $
 **/

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
