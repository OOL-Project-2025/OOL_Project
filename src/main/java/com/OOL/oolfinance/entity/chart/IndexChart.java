package com.OOL.oolfinance.entity.chart;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexChart
 * @date : 3/24/25 / 9:53 PM
 * @modifyed : $
 **/

@Entity
@Getter
@NoArgsConstructor
public class IndexChart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal openingPrice; //시가

    @Column
    private BigDecimal highPrice; //고가

    @Column
    private BigDecimal lowPrice; //저가

    @Column
    private BigDecimal closingPrice; //종가

    @Column
    private LocalDateTime timeStamp; //해당 캔들에서 마지막 틱이 저장된 시각

    @Column
    private LocalDateTime DateTime; //날짜 + 시간까지 저장

    @Column
    private String indexCode;

}
