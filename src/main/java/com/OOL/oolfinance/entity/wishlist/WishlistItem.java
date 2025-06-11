package com.OOL.oolfinance.entity.wishlist;

import com.OOL.oolfinance.dto.stock.StockDTO;
import com.OOL.oolfinance.entity.stock.Stock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : HeartListItem
 * @date : 2/20/25 / 2:39 PM
 * @modifyed : $
 **/

@Entity
@Getter
@NoArgsConstructor
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;

    @ManyToOne
    @JoinColumn(name = "stockCode")
    private Stock stockInfo;
    
    public WishlistItem(Wishlist wishlist, Stock stockInfo) {
        this.wishlist = wishlist;
        this.stockInfo = stockInfo;
    }
    
    public Stock getStockInfo() {
        return stockInfo;
    }

    public StockDTO transferStockInfoToStockDTO() {
        StockDTO data = StockDTO.builder()
                .stockCode(stockInfo.getStockCode())
                .stockSymbol(stockInfo.getStockSymbol())
                .stockName(stockInfo.getStockName())
                .previousClose(stockInfo.getPreviousClose())
                .currentClose(stockInfo.getCurrentClose())
                .tradingValue(stockInfo.getTradingValue())
                .tradingVolume(stockInfo.getTradingVolume())
                .countryStatus(stockInfo.getCountryStatus())
                .build();

        return data;
    }

    public void setWishlist(Wishlist wishlist) {
	this.wishlist = wishlist;
    }
	
    public void setStockInfo(Stock stock) {
	this.stockInfo = stock;
    }

}
