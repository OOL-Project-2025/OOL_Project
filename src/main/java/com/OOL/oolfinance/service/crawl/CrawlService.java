package com.OOL.oolfinance.service.crawl;

import com.OOL.oolfinance.entity.chart.Chart;

import java.io.IOException;
import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : CrawlService
 * @date : 2025. 6. 20. / 오후 8:06
 * @modifyed :
 **/
public interface CrawlService {
    void jsoupTest() throws IOException;

    void crawlAndSaveCharts(String stockCode);

    List<Chart> crawlChart(String stockCode);
}
