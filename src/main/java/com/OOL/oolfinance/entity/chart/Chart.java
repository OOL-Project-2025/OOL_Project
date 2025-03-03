package com.OOL.oolfinance.entity.chart;

import java.time.LocalDateTime;

import com.OOL.oolfinance.entity.stock.Stock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor

public class Chart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String chartName;
	
	@Column
	private Double chartOpeningPrice; //시가
	
	@Column
	private Double chartHighPrice; //고가
	
	@Column
	private Double chartLowPrice; //저가
	
	@Column
	private Double chartTradePrice; //종가
	
	@Column
	private Long chartTimeStamp; //해당 캔들에서 마지막 틱이 저장된 시각
	
	@Column
	private String chartStockVolume; //해당 종목의 거래량
	
	@Column
	private String chartStockPrice; // 해당 종목의 거래금액
	
	@Column
	private LocalDateTime chartDateTime; //날짜 + 시간까지 저장
	
	@ManyToOne
   	@JoinColumn(name = "stockCode", referencedColumnName = "stockCode")
    	private Stock stock;
	
}
