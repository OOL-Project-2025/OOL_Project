package com.OOL.oolfinance.controller.wishlist;

import java.util.List;

import com.OOL.oolfinance.dto.WishlistResponse;
import com.OOL.oolfinance.dto.general.GeneralResponse;
import com.OOL.oolfinance.enums.StatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Wishlist", description = "찜 카테고리 관련 API")
public class WishlistRestController {

    private final WishlistCategoryService wishlistCategoryService;
    
 // ✅ 카테고리 추가
    @PostMapping
    @Operation(summary = "찜 카테고리 추가", description = "찜 카테고리를 추가하는 API", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = {
                    @ExampleObject(name = "someExample1", value = """ 
                    { 
                        "id" : null, 
                        "wishlistName" : "someValue2"
                    } 
                """)
            }
    )))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<GeneralResponse> addCategory(@RequestBody WishlistDTO dto, HttpSession session) {
        String memberId = (String) session.getAttribute("loginId");
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(GeneralResponse.builder()
                            .status(StatusEnum.UNAUTHORIZED)
                            .message("로그인이 필요합니다.")
                    .build());
        }

        wishlistCategoryService.categoryAdd(memberId, dto.getWishlistName());
        return ResponseEntity.ok(GeneralResponse.builder()
                        .status(StatusEnum.OK)
                        .message("success")
                .build());
    }
    
    @GetMapping
    @Operation(summary = "찜 카테고리 목록 가져오기", description = "내가 가진 찜 카테고리 목록을 가져오는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다.", content = @Content(mediaType = "application/json"))
    })
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

    @DeleteMapping("/{wishlistId}")
    @Operation(summary = "찜 카테고리 삭제", description = "해당 카테고리를 삭제할 때의 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "삭제 권한이 없습니다.", content = @Content(mediaType = "application/json"))
    })
    @Parameters(value = {
            @Parameter(name = "wishlistId", description = "찜 카테고리 id", example = "1")
    })
    public ResponseEntity<GeneralResponse> deleteWishlist(@PathVariable("wishlistId") Long wishlistId, HttpSession session) {
    	String memberId = (String) session.getAttribute("loginId");
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(GeneralResponse.builder()
                            .status(StatusEnum.UNAUTHORIZED)
                            .message("로그인이 필요합니다.")
                    .build());
        }

        boolean result = wishlistCategoryService.wishlistDelete(wishlistId, memberId);
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