package com.OOL.oolfinance.controller.crawl;

import com.OOL.oolfinance.service.crawl.CrawlService;
import com.OOL.oolfinance.service.crawl.StockInfoCrawlService;
import com.OOL.oolfinance.service.marketIndex.MarketIndexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : CrawlController
 * @date : 2025. 7. 21. / 오후 11:11
 * @modifyed :
 **/

@Slf4j
@RequestMapping(value = "/api/crawl")
@RestController
@RequiredArgsConstructor
@Tag(name = "Crawl", description = "크롤링 관련 API")
public class CrawlController {

    private final CrawlService crawlService;
    private final StockInfoCrawlService stockInfoCrawlService;
    private final MarketIndexService marketIndexService;

    @GetMapping(value = "/daily/chart")
    @Operation(summary = "일일 차트 크롤링", description = "차트를 크롤링하는 API")
    public void crawlDailyChart() {
        log.info("크롤링 시작");

        crawlService.crawlAll();
    }

    @GetMapping(value = "/daily/index")
    @Operation(summary = "일일 지수 차트 크롤링", description = "지수 차트를 크롤링하는 API")
    public void crawlDailyIndexChart() {
        log.info("크롤링 시작");

        marketIndexService.updateDailyMarketIndexAndChart();
    }
}
