package com.OOL.oolfinance.controller.wishlist;

import java.util.List;

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
    public ResponseEntity<?> addCategory(@RequestBody WishlistDTO dto, HttpSession session) {
        String memberId = (String) session.getAttribute("loginId");
        if (memberId == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        wishlistCategoryService.categoryAdd(memberId, dto.getWishlistName());
        return ResponseEntity.ok().build();
    }
    
    @GetMapping
    public ResponseEntity<List<WishlistDTO>> getWishlist(HttpSession session) {
        String memberId = (String) session.getAttribute("loginId");
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<WishlistDTO> wishlists = wishlistCategoryService.listByMemberId(memberId);
        return ResponseEntity.ok(wishlists);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWishlist(@PathVariable("id") Long id, HttpSession session) {
    	String memberId = (String) session.getAttribute("loginId");
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        boolean result = wishlistCategoryService.wishlistDelete(id, memberId);
        if (result) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
    }

}