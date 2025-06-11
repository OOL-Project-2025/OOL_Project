package com.OOL.oolfinance.repository.stock;

import com.OOL.oolfinance.dto.detail.StockDetailDTO;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockInfoCustomRepository
 * @date : 2025. 4. 16. / 오후 9:02
 * @modifyed :
 **/
public interface StockInfoCustomRepository {
    List<StockDetailDTO> findAllByStock(String stockCode);
}
