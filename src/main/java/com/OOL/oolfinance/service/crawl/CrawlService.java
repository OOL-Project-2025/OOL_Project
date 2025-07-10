package com.OOL.oolfinance.service.crawl;

import com.OOL.oolfinance.entity.chart.Chart;
import com.OOL.oolfinance.entity.stock.Stock;

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

    void crawlAll();

    void crawlAndSaveCharts(Stock stock);

    List<Chart> crawlChart(String stockCode);

    void crawlKoreanStocks() throws IOException;

    void crawlSP500Stocks() throws IOException;
}
