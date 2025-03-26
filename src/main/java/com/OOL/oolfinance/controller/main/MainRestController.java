package com.OOL.oolfinance.controller.main;

import com.OOL.oolfinance.dto.index.IndexResponse;
import com.OOL.oolfinance.enums.IndexStatus;
import com.OOL.oolfinance.service.index.IndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : MainRestController
 * @date : 3/26/25 / 11:44 PM
 * @modifyed : $
 **/
@RestController
@RequiredArgsConstructor
public class MainRestController {
    private final IndexService indexService;

    @GetMapping(value = "/api/main/indices")
    public List<IndexResponse> getIndicesList(IndexStatus requestStatus) {

        return indexService.fetchIndexList(requestStatus);
    }

}
