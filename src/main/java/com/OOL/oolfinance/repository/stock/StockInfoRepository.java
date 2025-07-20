package com.OOL.oolfinance.repository.stock;

import com.OOL.oolfinance.entity.stock.Stock;
import com.OOL.oolfinance.entity.stock.StockInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockInfoRepository
 * @date : 2025. 4. 16. / 오후 9:02
 * @modifyed :
 **/

@Repository
public interface StockInfoRepository extends JpaRepository<StockInfo, Long>, StockInfoCustomRepository {

    boolean existsStockInfoByStockAndEarningsDate(Stock stock, String earningsDate);
}
