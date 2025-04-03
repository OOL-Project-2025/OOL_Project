package com.OOL.oolfinance.repository.index;

import com.OOL.oolfinance.dto.main.IndexDTO;
import com.OOL.oolfinance.enums.IndexStatus;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexCustomRepository
 * @date : 3/24/25 / 10:27 PM
 * @modifyed : $
 **/
public interface IndexCustomRepository {
    List<IndexDTO> findAllIndicesByIndexStatus(IndexStatus requestStatus);
}
