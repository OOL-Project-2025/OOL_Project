package com.OOL.oolfinance.service.wishlist;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.OOL.oolfinance.dto.WishlistDTO;
import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.entity.wishlist.Wishlist;
import com.OOL.oolfinance.repository.member.MemberRepository;
import com.OOL.oolfinance.repository.wishlist.WishlistCategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WishlistCategoryServiceImpl implements WishlistCategoryService {
	
	private final WishlistCategoryRepository wishlistCategoryRepository;
	private final MemberRepository memberRepository;
	
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
	public boolean wishlistDelete(Long id, Member member) {
	    Optional<Wishlist> optionalWishlist = wishlistCategoryRepository.findById(id);

	    if (optionalWishlist.isPresent()) {
	        Wishlist wishlist = optionalWishlist.get();

	        // 본인의 카테고리인지 확인
	        if (wishlist.getMember() != null && wishlist.getMember().getProvider().equals(member.getProvider()) && wishlist.getMember().getProviderId().equals(member.getProviderId())) {
	            wishlistCategoryRepository.deleteById(id);
	            return true;
	        }
	    }

	    return false;
	}


	@Override
	public boolean categoryAdd(Member member, String categoryName) {
	    if (member == null) {
	        return false;
	    }

	    Wishlist wishlist = new Wishlist(categoryName);
	    wishlist.setMember(member);

	    wishlistCategoryRepository.save(wishlist);
	    return true;
	}
	
	@Override
	public List<WishlistDTO> listByMember(Member member) {
        if (member == null) {
            return List.of(); // 로그인 안 된 경우 빈 목록
        }
        List<Wishlist> wishlists = wishlistCategoryRepository.findByMember(member);
        return WishlistDTO.of(wishlists);
    }

}
