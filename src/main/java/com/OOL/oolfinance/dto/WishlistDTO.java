package com.OOL.oolfinance.dto;

import java.util.ArrayList;
import java.util.List;

import com.OOL.oolfinance.entity.wishlist.Wishlist;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(name = "WishlistDTO", description = "찜 카테고리 추가 DTO")
public class WishlistDTO {

	@Schema(name = "id", type = "Long", description = "찜 카테고리 id")
	Long id;

	@Schema(name = "wishlistName", type = "String", description = "찜 카테고리 명")
	String wishlistName;
	// int sortValue;
	// boolean usingYn;
	
	public static List<WishlistDTO> of (List<Wishlist> wishlists) {
		if (wishlists != null) {
			List<WishlistDTO> wishlistCategoryList = new ArrayList<>();
			for(Wishlist x : wishlists) {
				wishlistCategoryList.add(of(x));
			}
			return wishlistCategoryList;
		}
		return null;
	}
	
	public static WishlistDTO of (Wishlist wishlist) {
		return WishlistDTO.builder()
				.id(wishlist.getId())
				.wishlistName(wishlist.getWishlistName())
				.build();
				
	}
	
}
