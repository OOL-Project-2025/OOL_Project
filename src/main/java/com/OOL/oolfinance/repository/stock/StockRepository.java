package com.OOL.oolfinance.repository.stock;

import com.OOL.oolfinance.entity.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockRepository
 * @date : 3/24/25 / 10:43 PM
 * @modifyed : $
 **/

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}
