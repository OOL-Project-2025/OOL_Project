package com.OOL.oolfinance.controller.wishlist;

import java.util.List;

import com.OOL.oolfinance.dto.WishlistResponse;
import com.OOL.oolfinance.dto.general.GeneralResponse;
import com.OOL.oolfinance.enums.StatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OOL.oolfinance.dto.WishlistDTO;
import com.OOL.oolfinance.service.wishlist.WishlistCategoryService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistRestController {

    private final WishlistCategoryService wishlistCategoryService;
    
 // ✅ 카테고리 추가
    @PostMapping
    public ResponseEntity<GeneralResponse> addCategory(@RequestBody WishlistDTO dto, HttpSession session) {
        String memberId = (String) session.getAttribute("loginId");
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(GeneralResponse.builder()
                            .status(StatusEnum.UNAUTHORIZED)
                            .message("로그인이 필요합니다.")
                    .build());
        }

        wishlistCategoryService.categoryAdd(memberId, dto.getWishlistName());
        return ResponseEntity.ok().build();
    }
    
    @GetMapping
    public ResponseEntity<WishlistResponse> getWishlist(HttpSession session) {
        String memberId = (String) session.getAttribute("loginId");
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(WishlistResponse.builder()
                            .status(StatusEnum.UNAUTHORIZED)
                            .message("로그인이 필요합니다.")
                    .build());
        }

        List<WishlistDTO> data = wishlistCategoryService.listByMemberId(memberId);


        return ResponseEntity.ok(WishlistResponse.builder()
                        .status(StatusEnum.OK)
                        .message("success")
                        .data(data)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteWishlist(@PathVariable("id") Long id, HttpSession session) {
    	String memberId = (String) session.getAttribute("loginId");
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(GeneralResponse.builder()
                            .status(StatusEnum.UNAUTHORIZED)
                            .message("로그인이 필요합니다.")
                    .build());
        }

        boolean result = wishlistCategoryService.wishlistDelete(id, memberId);
        if (result) {
            return ResponseEntity.ok(GeneralResponse.builder()
                            .status(StatusEnum.OK)
                            .message("success")
                    .build());
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(GeneralResponse.builder()
                        .status(StatusEnum.FORBIDDEN)
                        .message("삭제 권한이 없습니다.")
                .build());
    }

}