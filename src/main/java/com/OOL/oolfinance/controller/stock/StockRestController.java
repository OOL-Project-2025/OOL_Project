package com.OOL.oolfinance.controller.stock;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OOL.oolfinance.entity.stock.Stock;
import com.OOL.oolfinance.entity.wishlist.WishlistItem;
import com.OOL.oolfinance.repository.stock.StockRepository;
import com.OOL.oolfinance.repository.wishlist.WishlistItemRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stocks")
public class StockRestController {

    private final StockRepository stockRepository;
    private final WishlistItemRepository wishlistItemRepository;
    
    // 전체 주식 정보 리스트 반환
    @GetMapping
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    // 특정 주식 코드로 개별 조회
    @GetMapping("/{stockCode}")
    public Optional<Stock> getStockByCode(@PathVariable String stockCode) {
        return stockRepository.findById(stockCode);
    }
    
 // 로그인한 사용자의 위시리스트에 포함된 주식 목록 반환 (세션 기반)
    @GetMapping("/wishlist")
    public List<Stock> getStocksInUserWishlist(HttpSession session) {
        String memberId = (String) session.getAttribute("loginId");

        if (memberId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        List<WishlistItem> items = wishlistItemRepository.findByWishlist_Member_MemberId(memberId);
        
        return items.stream()
                .map(WishlistItem::getStockInfo)
                .distinct()
                .toList();
    }
}
