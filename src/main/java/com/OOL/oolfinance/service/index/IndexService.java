package com.OOL.oolfinance.service.index;

import com.OOL.oolfinance.dto.main.IndexDTO;
import com.OOL.oolfinance.enums.IndexStatus;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexService
 * @date : 3/24/25 / 10:41 PM
 * @modifyed : $
 **/
public interface IndexService {
    List<IndexDTO> fetchIndexList(IndexStatus requestStatus);
}
