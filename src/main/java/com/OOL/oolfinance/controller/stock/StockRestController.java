package com.OOL.oolfinance.controller.stock;

import java.util.List;
import java.util.Optional;

import com.OOL.oolfinance.dto.general.GeneralResponse;
import com.OOL.oolfinance.dto.stock.StockDTO;
import com.OOL.oolfinance.dto.stock.StockResponse;
import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.enums.StatusEnum;
import com.OOL.oolfinance.service.stock.StockService;
import com.OOL.oolfinance.service.wishlist.WishlistItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@Tag(name = "Stock", description = "주식 종목 관련 API")
public class StockRestController {

    private final StockService stockService;
    private final WishlistItemService wishlistItemService;

    // 전체 주식 정보 리스트 반환
    @GetMapping
    @Operation(summary = "주식 리스트 조회", description = "주식 정보를 반환하기 위한 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "fail", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<StockResponse> getAllStocks() {
        List<StockDTO> data = stockService.getAllStock();

        StockResponse response;

        if (data.isEmpty()) {
            response = StockResponse.builder()
                    .status(StatusEnum.BAD_REQUEST)
                    .message("fail")
                    .data(null)
                    .build();
        } else {
            response = StockResponse.builder()
                    .status(StatusEnum.OK)
                    .message("success")
                    .data(data)
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    // 특정 주식 코드로 개별 조회
    @GetMapping("/{stockCode}")
    @Operation(summary = "주식 개별 조회", description = "주식 정보를 반환하기 위한 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "fail", content = @Content(mediaType = "application/json"))
    })
    @Parameters(value = {
            @Parameter(name = "stockCode", description = "종목 코드", example = "11111"),
    })
    public ResponseEntity<GeneralResponse<StockDTO>> getStockByCode(@PathVariable String stockCode) {
        return ResponseEntity.ok(GeneralResponse.<StockDTO>builder()
                        .status(StatusEnum.OK)
                        .message("success")
                        .data(stockService.getOneStock(stockCode))
                        .build());
    }
    
 // 로그인한 사용자의 위시리스트에 포함된 주식 목록 반환 (세션 기반)
//    @GetMapping("/wishlist")
//    public List<Stock> getStocksInUserWishlist(HttpSession session) {
//        String memberId = (String) session.getAttribute("loginId");
//
//        if (memberId == null) {
//            throw new RuntimeException("로그인이 필요합니다.");
//        }
//
//        List<WishlistItem> items = wishlistItemRepository.findByWishlist_Member_MemberId(memberId);
//
//        return items.stream()
//                .map(WishlistItem::getStockInfo)
//                .distinct()
//                .toList();
//    }

    @GetMapping("/wishlist")
    @Operation(summary = "위시리스트 내 주식 목록 조회", description = "위시리스트의 주식 목록을 조회하기 위한 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "fail", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<StockResponse> getStocksInUserWishlist(@AuthenticationPrincipal Member member) {
        if (member == null) {
            StockResponse response = StockResponse.builder()
                    .status(StatusEnum.UNAUTHORIZED)
                    .message("로그인이 필요합니다.")
                    .data(null)
                    .build();
            return ResponseEntity.status(401).body(response);
        }

        List<StockDTO> data = wishlistItemService.getStockDTOsInUserWishlist(member);

        StockResponse response = StockResponse.builder()
                .status(StatusEnum.OK)
                .message("success")
                .data(data)
                .build();

        return ResponseEntity.ok(response);
    }
}
