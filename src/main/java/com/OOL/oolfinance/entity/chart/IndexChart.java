package com.OOL.oolfinance.entity.chart;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@AllArgsConstructor
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
    private LocalDateTime dateTime; //날짜 + 시간까지 저장

    @Column
    private String indexCode;

}
