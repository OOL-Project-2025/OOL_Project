package com.OOL.oolfinance.service.marketIndex;

import com.OOL.oolfinance.dto.main.IndexDTO;
import com.OOL.oolfinance.dto.stock.BondRequest;
import com.OOL.oolfinance.dto.stock.BondResponseWrapper;
import com.OOL.oolfinance.dto.stock.IndexRequest;
import com.OOL.oolfinance.dto.stock.IndexResponseWrapper;
import com.OOL.oolfinance.entity.chart.IndexChart;
import com.OOL.oolfinance.entity.stock.MarketIndex;
import com.OOL.oolfinance.enums.IndexStatus;
import com.OOL.oolfinance.repository.chart.IndexChartRepository;
import com.OOL.oolfinance.repository.marketIndex.MarketIndexRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexServiceImpl
 * @date : 3/24/25 / 10:41 PM
 * @modifyed : $
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketIndexServiceImpl implements MarketIndexService {

    private final MarketIndexRepository marketIndexRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final IndexChartRepository indexChartRepository;

    @Value("${custom.bond-uri}")
    String bondAndCommodityURI;
    @Value("${custom.index-uri}")
    private String indexURI;

    @Override
    public List<IndexDTO> fetchIndexList(IndexStatus requestStatus) {
        List<IndexDTO> indices = marketIndexRepository.findAllIndicesByIndexStatus(requestStatus);

        return indices;
    }

    @Override
    public void setMarketIndex() {
        MarketIndex marketIndex1 = MarketIndex.builder()
                .indexCode("KR3YT")
                .indexSymbol("KR")
                .indexName("한국 국채 3년")
                .status(IndexStatus.BOND)
                .build();

        MarketIndex marketIndex2 = MarketIndex.builder()
                .indexCode("US3YT")
                .indexSymbol("USA")
                .indexName("미국 국채 3년")
                .status(IndexStatus.BOND)
                .build();

        MarketIndex marketIndex3 = MarketIndex.builder()
                .indexCode("KOSPI")
                .indexSymbol("KR")
                .indexName("코스피")
                .status(IndexStatus.STOCK_INDEX)
                .build();

        MarketIndex marketIndex4 = MarketIndex.builder()
                .indexCode("GCcv1")
                .indexSymbol("USA")
                .indexName("국제 금")
                .status(IndexStatus.COMMODITY)
                .build();
        if (marketIndexRepository.findByIndexCode(marketIndex1.getIndexCode()) == null) {
            marketIndexRepository.save(marketIndex1);
        }
        if (marketIndexRepository.findByIndexCode(marketIndex2.getIndexCode()) == null) {
            marketIndexRepository.save(marketIndex2);
        }
        if (marketIndexRepository.findByIndexCode(marketIndex3.getIndexCode()) == null) {
            marketIndexRepository.save(marketIndex3);
        }
        if (marketIndexRepository.findByIndexCode(marketIndex4.getIndexCode()) == null) {
            marketIndexRepository.save(marketIndex4);
        }
    }

    @Override
    public void updateDailyMarketIndexAndChart() {
        List<MarketIndex> list = marketIndexRepository.findAll();
        for (int i = 0; i < list.size(); i++) {
            saveIndexChart(list.get(i));
            updateMarketIndex(list.get(i));
            try {
                Thread.sleep(500); // 1초 대기 (500~1500ms 랜덤도 추천)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void updateMarketIndex(MarketIndex marketIndex) {
        List<IndexChart> chart = indexChartRepository.findTop2ByIndexCodeOrderByDateTimeDesc(marketIndex.getIndexCode());

        marketIndex.updateDailyData(chart.get(1).getClosingPrice(), chart.get(0).getClosingPrice());
        marketIndexRepository.save(marketIndex);
    }

    @Override
    public void saveIndexChart(MarketIndex marketIndex) {
        List<BondRequest> bondRequests = null;
        List<IndexRequest> indexRequests = null;
        if (marketIndex.getStatus().equals(IndexStatus.STOCK_INDEX)) {
            indexRequests = fetchIndexData(marketIndex, 3);
        } else {
            bondRequests = fetchBondData(marketIndex, 3);
        }

        IndexChart latestChart = indexChartRepository
                .findTopByIndexCodeOrderByDateTimeDesc(marketIndex.getIndexCode());

        LocalDateTime lastSavedTime = latestChart == null ? null : latestChart.getDateTime();

        Stream<IndexChart> chartStream;

        if (marketIndex.getStatus().equals(IndexStatus.STOCK_INDEX)) {
            chartStream = indexRequests.stream()
                    .map(item -> {
                        LocalDateTime time = parseToLocalDateTime(item.getLocalTradedAt());
                        return IndexChart.builder()
                                .indexCode(marketIndex.getIndexCode())
                                .openingPrice(new BigDecimal(item.getOpenPrice().replace(",", "")))
                                .highPrice(new BigDecimal(item.getHighPrice().replace(",", "")))
                                .lowPrice(new BigDecimal(item.getLowPrice().replace(",", "")))
                                .closingPrice(new BigDecimal(item.getClosePrice().replace(",", "")))
                                .dateTime(time)
                                .build();
                    });
        } else {
            chartStream = bondRequests.stream()
                    .map(item -> {
                        LocalDateTime time = parseToLocalDateTime(item.getLocalTradedAt());
                        return IndexChart.builder()
                                .indexCode(marketIndex.getIndexCode())
                                .openingPrice(new BigDecimal(item.getOpenPrice().replace(",", "")))
                                .highPrice(new BigDecimal(item.getHighPrice().replace(",", "")))
                                .lowPrice(new BigDecimal(item.getLowPrice().replace(",", "")))
                                .closingPrice(new BigDecimal(item.getClosePrice().replace(",", "")))
                                .dateTime(time)
                                .build();
                    });
        }

        List<IndexChart> newCharts = chartStream
                .filter(chart -> lastSavedTime == null || chart.getDateTime().isAfter(lastSavedTime))
                .toList();

        indexChartRepository.saveAll(newCharts);
    }

    private LocalDateTime parseToLocalDateTime(String input) {
        if (input.length() == 10) {
            return LocalDate.parse(input).atStartOfDay();
        }
        return OffsetDateTime.parse(input).toLocalDateTime();
    }

    private List<IndexRequest> fetchIndexData(MarketIndex marketIndex, int maxPage) {
        String reutersCode = marketIndex.getIndexCode();
        List<IndexRequest> list = new ArrayList<>();

        for (int i = 1; i <= maxPage; i++) {
            try {
                URI uri = bondURIBuilder(marketIndex, i);

                HttpHeaders headers = new HttpHeaders();
                headers.set("User-Agent", "Mozilla/5.0");

                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<String> response = restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        entity,
                        String.class
                );
                String body = response.getBody();

                List<IndexRequest> pageData = objectMapper.readValue(
                        response.getBody(),
                        new TypeReference<List<IndexRequest>>() {}
                );
                if (pageData.isEmpty()) break; // 더 이상 데이터가 없으면 종료
                list.addAll(pageData);
            } catch (Exception e) {
                log.error("❌ [{}] 페이지 {}에서 국채 데이터 수집 실패: {}", reutersCode, i, e.getMessage(), e);
                break;
            }
        }

        return list;
    }

    private List<BondRequest> fetchBondData(MarketIndex marketIndex, int maxPage) {
        String reutersCode = marketIndex.getIndexCode();
        List<BondRequest> list = new ArrayList<>();

        for (int i = 1; i <= maxPage; i++) {
            try {
                URI uri = bondURIBuilder(marketIndex, i);

                HttpHeaders headers = new HttpHeaders();
                headers.set("User-Agent", "Mozilla/5.0");

                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<String> response = restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        entity,
                        String.class
                );
                String body = response.getBody();

                BondResponseWrapper wrapper = objectMapper.readValue(body, BondResponseWrapper.class);
                List<BondRequest> pageData = wrapper.getResult();
                if (pageData.isEmpty()) break; // 더 이상 데이터가 없으면 종료
                list.addAll(pageData);

            } catch (Exception e) {
                log.error("❌ [{}] 페이지 {}에서 국채 데이터 수집 실패: {}", reutersCode, i, e.getMessage(), e);
                break;
            }
        }

        return list;
    }

    private URI bondURIBuilder(MarketIndex marketIndex, int page) {
        IndexStatus status = marketIndex.getStatus();

        String baseUri = "";

        URI uri = null;

        switch (status) {
            case IndexStatus.BOND:
                uri = UriComponentsBuilder
                        .fromHttpUrl(bondAndCommodityURI)
                        .queryParam("category", "bond")
                        .queryParam("reutersCode", marketIndex.getIndexCode() + "=RR")
                        .queryParam("page", page)
                        .build()
                        .encode()  // 중요!
                        .toUri();
                break;
            case IndexStatus.COMMODITY:
                uri = UriComponentsBuilder
                        .fromHttpUrl(bondAndCommodityURI)
                        .queryParam("category", "metals")
                        .queryParam("reutersCode", marketIndex.getIndexCode())
                        .queryParam("page", page)
                        .build()
                        .encode()  // 중요!
                        .toUri();
                break;
            case IndexStatus.STOCK_INDEX:
                uri = UriComponentsBuilder
                        .fromHttpUrl(indexURI + marketIndex.getIndexCode() + "/price")
                        .queryParam("pageSize", 10)
                        .queryParam("page", page)
                        .build()
                        .encode()  // 중요!
                        .toUri();
                System.out.println(uri);
                break;
        }

        return uri;
    }
}
