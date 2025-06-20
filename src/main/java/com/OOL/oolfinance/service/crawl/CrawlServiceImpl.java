package com.OOL.oolfinance.service.crawl;

import com.OOL.oolfinance.entity.chart.Chart;
import com.OOL.oolfinance.repository.chart.ChartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : CrawlServiceImpl
 * @date : 2025. 6. 20. / 오후 8:07
 * @modifyed :
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlServiceImpl implements CrawlService {

    private final ChartRepository chartRepository;

    @Override
    public void jsoupTest() throws IOException {
        Document doc = Jsoup.connect("https://finance.naver.com/item/main.nhn?code=005930")
                .userAgent("Mozilla/5.0")
                .get();

        System.out.println(doc.title());
    }

    @Override
    public void crawlAndSaveCharts(String stockCode) {
        List<Chart> chartList = crawlChart(stockCode);
        for (Chart chart : chartList) {
            boolean exists = chartRepository.existsByStockCodeAndChartDateTime(
                    chart.getStockCode(), chart.getChartDateTime());

            if (!exists) {
                log.info("최신 데이터 : 저장 실행");
                chartRepository.save(chart);
            } else {
                log.info("저장된 데이터 : 무시");
            }
        }
    }

    @Override
    public List<Chart> crawlChart(String stockCode) {
        List<Chart> chartList = new ArrayList<>();

        try {
            // 날짜는 최근 며칠만 조회 (네이버는 최대 100일 제공)
            String endDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String startDate = LocalDate.now().minusDays(30).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

            String url = "https://api.finance.naver.com/siseJson.naver"
                    + "?symbol=" + stockCode
                    + "&requestType=1"
                    + "&startTime=" + startDate
                    + "&endTime=" + endDate
                    + "&timeframe=day";

            Connection.Response response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0")
                    .execute();

            String body = response.body();
            body = body.replaceAll("[\\[\\]]", ""); // 대괄호 제거
            String[] lines = body.split("\n");

            for (String line : lines) {
                String[] parts = line.replace("\"", "").split(",");

                if (parts.length != 7 || parts[0].contains("날짜")) continue;

                String rawDate = parts[0].trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                LocalDate date = LocalDate.parse(rawDate, formatter);
                LocalDateTime dateTime = date.atTime(15, 30);


                Chart chart = Chart.builder()
                        .stockCode(stockCode)
                        .chartDateTime(dateTime)
                        .chartOpeningPrice(toDecimal(parts[1]))
                        .chartHighPrice(toDecimal(parts[2]))
                        .chartLowPrice(toDecimal(parts[3]))
                        .chartClosingPrice(toDecimal(parts[4]))
                        .chartStockVolume(toDecimal(parts[5]))
                        .chartStockPrice(toDecimal(parts[6])) // 정확한 거래금액!
                        .build();

                chartList.add(chart);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return chartList;
    }

    private BigDecimal toDecimal(String str) {
        try {
            return new BigDecimal(str.replace(",", "").trim());
        } catch (Exception e) {
            return null;
        }
    }
}
