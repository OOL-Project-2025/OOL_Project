package com.OOL.oolfinance.scheduler.crawl;

import com.OOL.oolfinance.service.crawl.CrawlService;
import com.OOL.oolfinance.service.crawl.StockInfoCrawlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : Scheduler
 * @date : 2025. 7. 7. / 오후 11:28
 * @modifyed :
 **/

@Component
@Slf4j
@RequiredArgsConstructor
public class Scheduler {

    private final StockInfoCrawlService stockInfoCrawlService;
    private final CrawlService crawlService;

//    @Scheduled(cron = "0 2 3 * * MON-FRI") // 초 분 시 일 월 요일
    public void chartAndStockInfoCrawlScheduler() {
        log.info("스케줄링 시작");
        stockInfoCrawlService.crawlAll();
        crawlService.crawlAll();
    }
}
