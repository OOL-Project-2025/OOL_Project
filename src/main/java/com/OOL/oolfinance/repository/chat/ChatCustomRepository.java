package com.OOL.oolfinance.repository.chat;

import com.OOL.oolfinance.dto.chat.ChatDTO;
import com.OOL.oolfinance.dto.chat.ChatHistoryDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChatCustomRepository
 * @date : 2025. 5. 8. / 오후 8:11
 * @modifyed :
 **/
public interface ChatCustomRepository {
    List<ChatHistoryDTO> findByChatIdLessThanOrderByChatIdDesc(long chatId, Pageable pageable);
}
