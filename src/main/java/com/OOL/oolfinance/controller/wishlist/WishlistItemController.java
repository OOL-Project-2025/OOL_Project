package com.OOL.oolfinance.controller.wishlist;

import java.util.List;

import com.OOL.oolfinance.dto.general.GeneralResponse;
import com.OOL.oolfinance.dto.stock.StockDTO;
import com.OOL.oolfinance.dto.stock.StockResponse;
import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.enums.StatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<GeneralResponse> addStockToWishlist(@RequestBody StockRequestDTO request,
                                                              @AuthenticationPrincipal Member member) {
        // 세션에서 로그인된 사용자 ID 추출
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(GeneralResponse.<String>builder()
                    .status(StatusEnum.UNAUTHORIZED)
                    .message("로그인이 필요합니다.")
                    .build());
        }

        GeneralResponse<String> response;
        try {
            wishlistItemService.addStockToWishlist(member, request.getWishlistId(), request.getStockCode());
            return ResponseEntity.ok(GeneralResponse.<String>builder()
                    .status(StatusEnum.OK)
                    .message("찜 목록에 추가되었습니다.")
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(GeneralResponse.<String>builder()
                    .status(StatusEnum.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GeneralResponse.<String>builder()
                    .status(StatusEnum.INTERNAL_SERVER_ERROR)
                    .message("서버 오류가 발생했습니다.")
                    .build());
        }
    }

    @GetMapping
    public ResponseEntity<StockResponse> getUserWishlistStocks(@AuthenticationPrincipal Member member) {
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(StockResponse.builder()
                            .status(StatusEnum.UNAUTHORIZED)
                            .message("로그인이 필요합니다.")
                            .data(null)
                    .build());
        }

        List<StockDTO> data = wishlistItemService.getStockDTOsInUserWishlist(member);
        return ResponseEntity.ok(StockResponse.builder()
                .status(StatusEnum.OK)
                .message("success")
                .data(data)
                .build());
    }
}