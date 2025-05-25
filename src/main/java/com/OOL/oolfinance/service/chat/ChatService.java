package com.OOL.oolfinance.service.chat;

import com.OOL.oolfinance.dto.chat.ChatDTO;
import com.OOL.oolfinance.dto.chat.ChatHistoryDTO;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChatService
 * @date : 2025. 5. 12. / 오후 11:41
 * @modifyed :
 **/
public interface ChatService {
    void chatSave(ChatDTO chatDTO);

    List<ChatHistoryDTO> getPreviousChat(long chatId);
}
