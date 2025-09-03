package com.OOL.oolfinance.service.wishlist;

import java.util.List;

import com.OOL.oolfinance.dto.WishlistDTO;
import com.OOL.oolfinance.entity.member.Member;


public interface WishlistCategoryService {
	
	//카테고리 신규 추가
	boolean wishlistAdd(String wishlistname);
	
	//카테고리 수정
	boolean wishlistUpdate(WishlistDTO parameter);
	
	//카테고리 삭제
	boolean wishlistDelete(Long id, Member member);

	List<WishlistDTO> Categorylist();

	boolean categoryAdd(Member member, String categoryName);
	
	List<WishlistDTO> listByMember(Member member);
}
