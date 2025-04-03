package com.OOL.oolfinance.service.wishlist;

import java.util.List;

import com.OOL.oolfinance.dto.WishlistDTO;


public interface WishlistCategoryService {
	
	List<WishlistDTO> list();
	//카테고리 신규 추가
	 
	boolean wishlistAdd(String wishlistname);
	
	//카테고리 수정
	boolean wishlistUpdate(WishlistDTO parameter);
	
	//카테고리 삭제
	boolean wishlistDelete(long id);

	List<WishlistDTO> Categorylist();

	boolean categoryAdd(String categoryName);
	
	
}
