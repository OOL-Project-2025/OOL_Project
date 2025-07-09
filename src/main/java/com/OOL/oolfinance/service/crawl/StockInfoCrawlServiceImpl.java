package com.OOL.oolfinance.service.crawl;

import com.OOL.oolfinance.entity.stock.Performance;
import com.OOL.oolfinance.entity.stock.Stock;
import com.OOL.oolfinance.entity.stock.StockInfo;
import com.OOL.oolfinance.repository.stock.PerformanceRepository;
import com.OOL.oolfinance.repository.stock.StockInfoRepository;
import com.OOL.oolfinance.repository.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StockInfoCrawlServiceImpl
 * @date : 2025. 6. 26. / 오전 12:42
 * @modifyed :
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class StockInfoCrawlServiceImpl implements StockInfoCrawlService {
    private final StockInfoRepository stockInfoRepository;
    private final StockRepository stockRepository;
    private final PerformanceRepository performanceRepository;

    @Value("${custom.stock-info-base-1}")
    String baseUri1;
    @Value("${custom.stock-info-base-2}")
    String baseUri2;
    @Value("${custom.stock-info-base-3}")
    String baseUri3;
    @Value("${custom.stock-info-base-4}")
    String baseUri4;

    @Override
    public void crawlAll() {
//        List<Stock> list = stockRepository.findAll();
        List<Stock> list = new ArrayList<>();
        list.add(stockRepository.findByStockCode("005490").orElseThrow(() -> new IllegalArgumentException("해당 코드 없음.")));
        list.add(stockRepository.findByStockCode("005930").orElseThrow(() -> new IllegalArgumentException("해당 코드 없음.")));
        list.add(stockRepository.findByStockCode("259960").orElseThrow(() -> new IllegalArgumentException("해당 코드 없음.")));
        for (Stock s : list) {
            log.info("크롤링 시작 [{}]", s.getStockCode());
            crawlOneStockInfo(s);
            try {
                Thread.sleep(500); // 1초 대기 (500~1500ms 랜덤도 추천)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 실제 Selenium + Jsoup 크롤링 로직
     */
    @Override
    public synchronized void crawlOneStockInfo(Stock stock) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--incognito"); // 세션 무시
        options.addArguments("--headless=new"); // 또는 "--headless"
//        options.addArguments("--disable-gpu");  // 그래픽 가속 비활성화 (필수는 아님)
//        options.addArguments("--no-sandbox");   // 리눅스에서 권장
//        options.addArguments("--disable-dev-shm-usage"); // 리눅스에서 메모리 제한 회피
        WebDriver driver = new ChromeDriver(options);

        try {
            String url = baseUri1 + stock.getStockCode() + baseUri2;
            driver.get(url);

            Document doc = Jsoup.parse(driver.getPageSource());

            Elements tables = doc.select("table.gHead01.all-width[summary*=주요재무정보]");
            Element table = tables.get(1); // 두 번째 테이블이 연간/분기 재무 데이터

            Elements yearHeaders = table.select("thead tr").get(1).select("th");

            List<String> years = new ArrayList<>();

            for (int i = 0; i < yearHeaders.size(); i++) {
                if (i >= 3) {
                    break;
                }
                years.add(yearHeaders.get(i).text().trim().split(" ")[0]);
            }

            Elements rows = table.select("tbody tr");
            Map<String, List<String>> rowMap = new HashMap<>();
            for (Element row : rows) {
                String label = row.selectFirst("th").text();
                List<Element> cols = row.select("td");

                List<String> values = new ArrayList<>();
                for (int i = 0; i < years.size(); i++) {
                    values.add(cols.get(i).text());
                }
                rowMap.put(label, values);
            }
            Map<String, List<String>> estimatedMap = new HashMap<>();
            for (Element row : rows) {
                String label = row.selectFirst("th").text();
                List<Element> cols = row.select("td");

                List<String> values = new ArrayList<>();
                values.add(cols.get(3).text());
                estimatedMap.put(label, values);
            }

            String url2 = baseUri3 + stock.getStockCode() + baseUri4;
            driver.get(url2);
            driver.findElement(By.id("val_td3")).click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//table[contains(@summary, 'IFRS연결 연간 투자분석 정보를 제공합니다.')]//tr/*[contains(normalize-space(.), '유동비율')]")
            ));

            Document doc2 = Jsoup.parse(driver.getPageSource());
            Elements tables2 = doc2.select("table.gHead01.all-width.data-list[summary*=IFRS연결 연간 투자분석 정보를 제공합니다.]");
            Element table2 = tables2.get(1);

            Elements rows2 = table2.select("tbody tr");
            Map<String, List<String>> rowMap2 = new HashMap<>();
            for (Element row : rows2) {
                Elements tdList = row.select("td");
                if (tdList.isEmpty()) continue;

                String label = tdList.get(0).text();
                List<String> values = new ArrayList<>();
                for (int i = 3; i < 3 + years.size(); i++) {
                    values.add(tdList.get(i).text());
                }
                rowMap2.put(label, values);
            }

            for (int i = 0; i < years.size(); i++) {
                StockInfo stockInfo = StockInfo.builder()
                        .stock(stock)
                        .per(parseDecimal(getValueSafely(rowMap, "PER(배)", i)))
                        .pbr(parseDecimal(getValueSafely(rowMap, "PBR(배)", i)))
                        .roe(parseDecimal(getValueSafely(rowMap, "ROE(%)", i)))
                        .debtToEquityRatio(parseDecimal(getValueSafely(rowMap, "부채비율", i)))
                        .currentRatio(parseDecimal(getValueSafely(rowMap2, "펼치기 유동비율", i)))
                        .interestCoverageRatio(parseDecimal(getValueSafely(rowMap2, "펼치기 이자보상배율", i)))
                        .totalDebt(parseDecimal(getValueSafely(rowMap, "부채총계", i)))
                        .totalEquity(parseDecimal(getValueSafely(rowMap, "자본총계", i)))
                        .operatingIncome(parseDecimal(getValueSafely(rowMap, "영업이익", i)))
                        .revenue(parseDecimal(getValueSafely(rowMap, "매출액", i)))
                        .netIncome(parseDecimal(getValueSafely(rowMap, "당기순이익", i)))
                        .dividendDate(null)
                        .dividendPerShare(parseDecimal(getValueSafely(rowMap, "현금DPS(원)", i)))
                        .dividendYield(parseDecimal(getValueSafely(rowMap, "현금배당수익률", i)))
                        .earningsDate(years.get(i))
                        .build();

                Performance performance = Performance.builder()
                        .stock(stock)
                        .estimatedEps(parseDecimal(getValueSafely(estimatedMap, "현금DPS(원)", 0)))
                        .earningsDate(yearHeaders.get(3).text().trim().split(" ")[0])
                        .estimatedNetRevenue(parseDecimal(getValueSafely(estimatedMap, "매출액", 0)))
                        .build();

                if (!stockInfoRepository.existsStockInfoByStockAndEarningsDate(stock, stockInfo.getEarningsDate())) {
                    stockInfoRepository.save(stockInfo);
                }
                if (!performanceRepository.existsPerformanceByEarningsDate(performance.getEarningsDate())) {
                    performanceRepository.save(performance);
                }
            }

        } catch (Exception e) {
            log.error("❌ 크롤링 실패 [{}]: {}", stock.getStockCode(), e.getMessage());
        } finally {
            driver.quit();
        }
    }

    private String getValueSafely(Map<String, List<String>> map, String key, int index) {
        List<String> list = map.get(key);
        if (list != null && index < list.size()) {
            return list.get(index);
        }
        return null;
    }

    private BigDecimal parseDecimal(String text) {
        try {
            String numeric = text.replaceAll("[^0-9.-]", "");
            return numeric.isEmpty() ? null : new BigDecimal(numeric);
        } catch (Exception e) {
            log.warn("숫자 변환 실패: {}", text);
            return null;
        }
    }

}
