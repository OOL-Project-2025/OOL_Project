package com.OOL.oolfinance.entity.chart;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.OOL.oolfinance.entity.stock.Stock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Chart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private String chartName;
	private Long id;
	
	@Column
	private BigDecimal chartOpeningPrice; //시가
	
	@Column
	private BigDecimal chartHighPrice; //고가
	
	@Column
	private BigDecimal chartLowPrice; //저가
	
	@Column
	private BigDecimal chartClosingPrice; //종가
	
//	@Column
//	private Long chartTimeStamp; //해당 캔들에서 마지막 틱이 저장된 시각
	
	@Column
	private BigDecimal chartStockVolume; //해당 종목의 거래량
	
	@Column
	private BigDecimal chartStockPrice; // 해당 종목의 거래금액
	
	@Column
	private LocalDateTime chartDateTime; //날짜 + 시간까지 저장
	
//	@ManyToOne
//   	@JoinColumn(name = "stockCode", referencedColumnName = "stockCode")
//	private Stock stock;

	@Column
	private String stockCode;
	
}
