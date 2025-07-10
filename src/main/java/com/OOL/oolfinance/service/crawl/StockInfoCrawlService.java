package com.OOL.oolfinance.service.crawl;

import com.OOL.oolfinance.entity.stock.Stock;
import com.OOL.oolfinance.entity.stock.StockInfo;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockInfoCrawlService
 * @date : 2025. 6. 26. / 오전 12:42
 * @modifyed :
 **/
public interface StockInfoCrawlService {

    void crawlAll();

    void crawlOneStockInfo(Stock stock);
}
