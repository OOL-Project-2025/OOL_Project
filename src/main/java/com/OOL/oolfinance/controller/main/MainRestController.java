package com.OOL.oolfinance.controller.main;

import com.OOL.oolfinance.dto.main.IndexDTO;
import com.OOL.oolfinance.dto.main.StockTableDTO;
import com.OOL.oolfinance.enums.CountryStatus;
import com.OOL.oolfinance.enums.IndexStatus;
import com.OOL.oolfinance.enums.StockSortType;
import com.OOL.oolfinance.service.index.IndexService;
import com.OOL.oolfinance.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : MainRestController
 * @date : 3/26/25 / 11:44 PM
 * @modifyed : $
 **/
@RestController
@RequiredArgsConstructor
public class MainRestController {
    private final IndexService indexService;
    private final StockService stockService;

    @GetMapping(value = "/api/main/indices")
    public List<IndexDTO> getIndicesList(@RequestParam(value = "requestStatus") IndexStatus requestStatus) {

        return indexService.fetchIndexList(requestStatus);
    }

    @GetMapping(value = "/api/main/stocks")
    public List<StockTableDTO> getStockList(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(value = "countryStatus") CountryStatus countryStatus,
                                            @RequestParam(value = "sort") StockSortType sortType) {
        return stockService.getStockTable(page, countryStatus, sortType);
    }
}
