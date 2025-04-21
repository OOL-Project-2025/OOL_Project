package com.OOL.oolfinance.repository.index;

import com.OOL.oolfinance.entity.stock.Index;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexRepository
 * @date : 3/24/25 / 10:25 PM
 * @modifyed : $
 **/

@Repository
public interface IndexRepository extends JpaRepository<Index, String>, IndexCustomRepository {
}
