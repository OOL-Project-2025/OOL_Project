package com.OOL.oolfinance.service.index;

import com.OOL.oolfinance.dto.index.IndexResponse;
import com.OOL.oolfinance.enums.IndexStatus;
import com.OOL.oolfinance.repository.index.IndexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class IndexServiceImpl implements IndexService {

    private final IndexRepository indexRepository;

    @Override
    public List<IndexResponse> fetchIndexList(IndexStatus requestStatus) {
        List<IndexResponse> indices = indexRepository.findAllIndicesByIndexStatus(requestStatus);

        return indices;
    }
}
