package com.OOL.oolfinance.repository.stock;

import java.util.List;
import java.util.Optional;

import com.OOL.oolfinance.enums.CountryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.OOL.oolfinance.entity.stock.Stock;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockRepository
 * @date : 3/24/25 / 10:43 PM
 * @modifyed : $
 **/

@Repository
public interface StockRepository extends JpaRepository<Stock, String>, StockCustomRepository {
    long countByCountryStatus(CountryStatus countryStatus);

    Optional<Stock> findByStockCode(String stockCode);

    List<Stock> findAllByCountryStatus(CountryStatus countryStatus);

}
