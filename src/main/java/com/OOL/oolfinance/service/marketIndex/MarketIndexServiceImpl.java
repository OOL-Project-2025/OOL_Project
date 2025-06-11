package com.OOL.oolfinance.service.marketIndex;

import com.OOL.oolfinance.dto.main.IndexDTO;
import com.OOL.oolfinance.enums.IndexStatus;
import com.OOL.oolfinance.repository.marketIndex.MarketIndexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexServiceImpl
 * @date : 3/24/25 / 10:41 PM
 * @modifyed : $
 **/

@Service
@RequiredArgsConstructor
public class MarketIndexServiceImpl implements MarketIndexService {

    private final MarketIndexRepository marketIndexRepository;

    @Override
    public List<IndexDTO> fetchIndexList(IndexStatus requestStatus) {
        List<IndexDTO> indices = marketIndexRepository.findAllIndicesByIndexStatus(requestStatus);

        return indices;
    }
}
