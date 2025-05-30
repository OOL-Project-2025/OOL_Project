package com.OOL.oolfinance.controller.wishlist;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OOL.oolfinance.dto.StockRequestDTO;
import com.OOL.oolfinance.entity.stock.Stock;
import com.OOL.oolfinance.service.wishlist.WishlistItemService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/wishlist/items")
@RequiredArgsConstructor
public class WishlistItemController {

    private final WishlistItemService wishlistItemService;

    @PostMapping("/add")
    public ResponseEntity<String> addStockToWishlist(@RequestBody StockRequestDTO request,
                                                     HttpSession session) {
        // 세션에서 로그인된 사용자 ID 추출
        String memberId = (String) session.getAttribute("loginId");

        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        try {
            wishlistItemService.addStockToWishlist(memberId, request.getWishlistId(), request.getStockCode());
            return ResponseEntity.ok("찜 목록에 추가되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Stock>> getUserWishlistStocks(HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");

        List<Stock> stocks = wishlistItemService.getStocksInUserWishlist(loginId);
        return ResponseEntity.ok(stocks);
    }
}