package com.OOL.oolfinance.service.stock;

import com.OOL.oolfinance.dto.main.StockTableDTO;
import com.OOL.oolfinance.entity.stock.Stock;
import com.OOL.oolfinance.enums.CountryStatus;
import com.OOL.oolfinance.enums.StockSortType;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockService
 * @date : 3/24/25 / 10:45 PM
 * @modifyed : $
 **/
public interface StockService {
    List<StockTableDTO> getStockTable(int page, CountryStatus countryStatus, StockSortType sortType);

}
