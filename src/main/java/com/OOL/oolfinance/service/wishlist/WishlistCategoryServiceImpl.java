package com.OOL.oolfinance.service.wishlist;

import java.util.List;

import org.springframework.stereotype.Service;

import com.OOL.oolfinance.dto.WishlistDTO;
import com.OOL.oolfinance.entity.wishlist.Wishlist;
import com.OOL.oolfinance.repository.wishlist.WishlistCategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WishlistCategoryServiceImpl implements WishlistCategoryService {
	
	private final WishlistCategoryRepository wishlistCategoryRepository;
	
	@Override
	public List<WishlistDTO> Categorylist() {
		List<Wishlist> wishlists = wishlistCategoryRepository.findAll();
		return WishlistDTO.of(wishlists);
	}
	
	@Override
	public boolean wishlistAdd(String wishlistName) {
		    // 카테고리명 중복 체크
		    Wishlist wishlist = new Wishlist(wishlistName);

		    wishlistCategoryRepository.save(wishlist);

		    return true;
		
	}
				
	@Override
	public boolean wishlistUpdate(WishlistDTO parameter) {
		return false;
	}
	
	@Override
	public boolean wishlistDelete(long id) {
		return false;
	}

	@Override
	public List<WishlistDTO> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean categoryAdd(String categoryName) {
		
		return wishlistAdd(categoryName);
	}
}
