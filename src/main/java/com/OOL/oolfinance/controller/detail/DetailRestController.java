package com.OOL.oolfinance.controller.detail;

import com.OOL.oolfinance.dto.detail.StockDetailDTO;
import com.OOL.oolfinance.service.stock.StockInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : DetailRestController
 * @date : 2025. 4. 16. / 오후 9:18
 * @modifyed :
 **/

@RestController
@RequiredArgsConstructor
public class DetailRestController {
    private final StockInfoService stockInfoService;

    @GetMapping(value = "/api/detail")
    public List<StockDetailDTO> getStockDetailAll(@RequestParam(value = "stockCode") String stockCode) {
        return stockInfoService.getStockDetails(stockCode);
    }
}
