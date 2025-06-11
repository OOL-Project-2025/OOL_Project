package com.OOL.oolfinance.repository.chat;

import com.OOL.oolfinance.entity.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ChatRepository
 * @date : 2025. 5. 8. / 오후 8:10
 * @modifyed :
 **/
public interface ChatRepository extends JpaRepository<Chat, Long>, ChatCustomRepository {
}
