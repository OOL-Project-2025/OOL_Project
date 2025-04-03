package com.OOL.oolfinance.dto;

import java.util.ArrayList;
import java.util.List;

import com.OOL.oolfinance.entity.wishlist.Wishlist;

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
public class WishlistDTO {
	
	Long id;
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
