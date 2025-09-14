package com.OOL.oolfinance.repository.chat;

import com.OOL.oolfinance.dto.chat.ChatDTO;
import static com.OOL.oolfinance.entity.chat.QChat.*;

import com.OOL.oolfinance.dto.chat.ChatHistoryDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChatCustomRepositoryImpl
 * @date : 2025. 5. 8. / 오후 8:11
 * @modifyed :
 **/

@Repository
@RequiredArgsConstructor
public class ChatCustomRepositoryImpl implements ChatCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChatHistoryDTO> findByChatIdLessThanOrderByChatIdDesc(long chatId, Pageable pageable) {

        return jpaQueryFactory.select(Projections.constructor(ChatHistoryDTO.class,
                chat.id,
                chat.provider,
                chat.providerId,
                chat.nickname,
                chat.message,
                chat.time))
                .from(chat)
                .where(chatId != -1 ? chat.id.lt(chatId) : null)
                .orderBy(chat.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
