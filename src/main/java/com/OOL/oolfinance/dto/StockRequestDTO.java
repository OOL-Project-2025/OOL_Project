package com.OOL.oolfinance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "StockRequestDTO", description = "찜 카테고리 내에 종목을 추가할 때 Request용 DTO")
public class StockRequestDTO {

    @Schema(name = "wishlistId", type = "Long", description = "찜 카테고리 id")
    private Long wishlistId;

    @Schema(name = "stockCode", type = "String", description = "주식 코드")
    private String stockCode;
    
    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }
}
