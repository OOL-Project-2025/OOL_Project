package com.OOL.oolfinance.service.stock;

import com.OOL.oolfinance.dto.detail.StockDetailDTO;
import com.OOL.oolfinance.entity.stock.StockInfo;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockInfoService
 * @date : 2025. 4. 16. / 오후 9:15
 * @modifyed :
 **/
public interface StockInfoService{
    List<StockDetailDTO> getStockDetails(String stockCode);

    void stockInfoSave(StockInfo stockInfo);
}
