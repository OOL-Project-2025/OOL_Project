package com.OOL.oolfinance.service.stock;

import com.OOL.oolfinance.dto.detail.StockDetailDTO;
import com.OOL.oolfinance.entity.stock.StockInfo;
import com.OOL.oolfinance.repository.stock.StockInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockInfoServiceImpl
 * @date : 2025. 4. 16. / 오후 9:15
 * @modifyed :
 **/

@Service
@RequiredArgsConstructor
public class StockInfoServiceImpl implements StockInfoService {
    private final StockInfoRepository stockInfoRepository;

    @Override
    public List<StockDetailDTO> getStockDetails(String stockCode) {
        return stockInfoRepository.findAllByStock(stockCode);
    }

    @Override
    public void stockInfoSave(StockInfo stockInfo) {
        stockInfoRepository.save(stockInfo);
    }
}
