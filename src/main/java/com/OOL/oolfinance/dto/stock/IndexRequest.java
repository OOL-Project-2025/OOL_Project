package com.OOL.oolfinance.dto.stock;

import lombok.Data;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : BondRequest
 * @date : 2025. 7. 8. / 오후 11:16
 * @modifyed :
 **/

@Data
public class IndexRequest {
    private String localTradedAt; // 날짜
    private String closePrice; // 종가
    private CompareToPreviousPrice compareToPreviousPrice; // 상승/하락상태
    private String compareToPreviousClosePrice; // 전일 대비 종가
    private String fluctuationsRatio; // 등락률
    private String openPrice; // 시가
    private String highPrice; // 고가
    private String lowPrice; // 저가

    @Data
    public static class CompareToPreviousPrice {
        private String code;
        private String text; // 상승/하락
        private String name;
    }
}
