package com.OOL.oolfinance.service.crawl;

import com.OOL.oolfinance.entity.chart.Chart;
import com.OOL.oolfinance.entity.stock.Stock;
import com.OOL.oolfinance.enums.CountryStatus;
import com.OOL.oolfinance.repository.chart.ChartRepository;
import com.OOL.oolfinance.repository.stock.StockRepository;
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
import java.util.Objects;

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
    private final StockRepository stockRepository;

    @Override
    public void crawlAll() {
        List<Stock> list = new ArrayList<>();
        list.add(stockRepository.findByStockCode("005490").orElseThrow(() -> new IllegalArgumentException("해당 코드 없음.")));
        list.add(stockRepository.findByStockCode("005930").orElseThrow(() -> new IllegalArgumentException("해당 코드 없음.")));
        list.add(stockRepository.findByStockCode("000720").orElseThrow(() -> new IllegalArgumentException("해당 코드 없음.")));

        for (Stock s : list) {
            crawlAndSaveCharts(s);
            try {
                Thread.sleep(500); // 1초 대기 (500~1500ms 랜덤도 추천)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void crawlAndSaveCharts(Stock stock) {
        List<Chart> chartList = crawlChart(stock.getStockCode());
        BigDecimal todayTradingValue = fetchTodayTradingValue(stock.getStockCode());
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

        List<Chart> latestCharts = chartRepository.findTop2ByStockCodeOrderByChartDateTimeDesc(stock.getStockCode());
        System.out.println(latestCharts.get(0).getChartTradingValue() + " 트레이딩밸류");
        if (latestCharts.get(0).getChartTradingValue().equals(new BigDecimal("0.00"))) {
            Chart c = latestCharts.get(0);
            c.updateChartTradingValue(todayTradingValue);
            chartRepository.save(c);
        }
        if (latestCharts.size() == 2) {
            Chart latest = latestCharts.get(0);
            Chart previous = latestCharts.get(1);

            stock.updatePriceAndVolume(
                    previous.getChartClosingPrice(),
                    latest.getChartClosingPrice(),
                    todayTradingValue,
                    latest.getChartTradingVolume()
            );
            stockRepository.save(stock);
        } else if (latestCharts.size() == 1) {
            Chart latest = latestCharts.get(0);

            stock.updatePriceAndVolume(
                    BigDecimal.valueOf(0),
                    latest.getChartClosingPrice(),
                    todayTradingValue,
                    latest.getChartTradingVolume()
            );
            stockRepository.save(stock);
        } else {
            log.warn("업데이트를 위한 차트 데이터가 부족합니다: {}", stock.getStockCode());
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
                        .chartTradingVolume(toDecimal(parts[5]))
                        .chartTradingValue(BigDecimal.valueOf(0))
                        .build();

                chartList.add(chart);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return chartList;
    }

    private BigDecimal fetchTodayTradingValue(String stockCode) {
        try {
            String url = "https://finance.naver.com/item/sise.naver?code=" + stockCode;
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .get();

            Element span = doc.selectFirst("span#_amount");
            if (span != null) {
                String raw = span.text().replace(",", "").trim();
                System.out.println(raw + "단위");
                return new BigDecimal(raw); // 백만 단위
            }

        } catch (Exception e) {
            log.error("거래대금 크롤링 실패 [{}]: {}", stockCode, e.getMessage());
            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }


    private BigDecimal toDecimal(String str) {
        try {
            return new BigDecimal(str.replace(",", "").trim());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void crawlKoreanStocks() throws IOException {
        String baseUrl = "https://finance.naver.com/sise/sise_market_sum.naver?sosok=0&page=";
        int totalPage = getTotalPage(baseUrl + "1"); // 페이지 수 동적 계산

        for (int page = 1; page <= totalPage; page++) {
            String url = baseUrl + page;
            Document doc = Jsoup.connect(url).get();

            Elements rows = doc.select("table.type_2 tbody tr");

            for (Element row : rows) {
                if (!row.hasAttr("onmouseover")) continue; // 빈 줄 필터링

                Element link = row.selectFirst("a[href*=code=]");
                if (link == null) continue;

                String name = link.text();
                String code = link.attr("href").split("code=")[1];

                if (!stockRepository.existsById(code)) {
                    Stock stock = Stock.builder()
                            .stockCode(code)
                            .stockName(name)
                            .stockSymbol(null)
                            .countryStatus(CountryStatus.KR)
                            .build();

                    stockRepository.save(stock);
                    log.info("저장 완료: {} ({})", name, code);
                }
            }

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }

    private int getTotalPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element lastPageElement = doc.selectFirst("td.pgRR a");
        if (lastPageElement != null) {
            String href = lastPageElement.attr("href"); // 예: /sise/sise_market_sum.naver?sosok=0&page=32
            String pageStr = href.substring(href.lastIndexOf("page=") + 5);
            return Integer.parseInt(pageStr);
        }
        return 1; // fallback
    }

    @Override
    public void crawlSP500Stocks() throws IOException {
        String url = "https://en.wikipedia.org/wiki/List_of_S%26P_500_companies";
        Document doc = Jsoup.connect(url).get();

        Element table = doc.selectFirst("table.wikitable");

        if (table != null) {
            Elements rows = table.select("tbody > tr");
            for (int i = 1; i < rows.size(); i++) { // 첫 번째는 헤더
                Elements cols = rows.get(i).select("td");
                if (cols.size() >= 2) {
                    String ticker = cols.get(0).text().trim();   // 종목코드
                    String name = cols.get(1).text().trim();     // 회사명

                    Stock stock = Stock.builder()
                            .stockCode(ticker)
                            .stockName(name)
                            .stockSymbol(null)
                            .countryStatus(CountryStatus.USA)
                            .build();
                    stockRepository.save(stock);
                }
            }
        }
    }
}
