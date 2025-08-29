package com.OOL.oolfinance.repository.stock;

import com.OOL.oolfinance.entity.stock.Performance;
import com.OOL.oolfinance.entity.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : PerformanceRepository
 * @date : 2025. 7. 3. / 오후 12:34
 * @modifyed :
 **/

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long>, PerformanceCustomRepository {
    boolean existsPerformanceByStockAndEarningsDate(Stock stock, String earningsDate);
}
