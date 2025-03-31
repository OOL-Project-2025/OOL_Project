package com.OOL.oolfinance.repository.stock;

import com.OOL.oolfinance.dto.main.StockTableDTO;
import com.OOL.oolfinance.enums.CountryStatus;
import com.OOL.oolfinance.enums.StockSortType;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockCustomRepository
 * @date : 3/24/25 / 10:44 PM
 * @modifyed : $
 **/
public interface StockCustomRepository {
    List<StockTableDTO> findByCountryStatus(Pageable pageable, CountryStatus countryStatus, StockSortType sortType);
}
