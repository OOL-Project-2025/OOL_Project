package com.OOL.oolfinance.service.stock;

import com.OOL.oolfinance.dto.main.StockTableDTO;
import com.OOL.oolfinance.entity.stock.Stock;
import com.OOL.oolfinance.enums.CountryStatus;
import com.OOL.oolfinance.enums.StockSortType;
import com.OOL.oolfinance.repository.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockServiceImpl
 * @date : 3/24/25 / 10:45 PM
 * @modifyed : $
 **/

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService{

    private final StockRepository stockRepository;

    @Override
    public List<StockTableDTO> getStockTable(int page, CountryStatus countryStatus, StockSortType sortType) {

        Pageable pageable = PageRequest.of(page, 10);

        return stockRepository.findByCountryStatus(pageable, countryStatus, sortType);

    }

}
