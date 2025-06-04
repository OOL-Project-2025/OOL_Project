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
	public boolean wishlistDelete(Long id, String memberId) {
	    Optional<Wishlist> optionalWishlist = wishlistCategoryRepository.findById(id);

	    if (optionalWishlist.isPresent()) {
	        Wishlist wishlist = optionalWishlist.get();

	        // 본인의 카테고리인지 확인
	        if (wishlist.getMember() != null && wishlist.getMember().getMemberId().equals(memberId)) {
	            wishlistCategoryRepository.deleteById(id);
	            return true;
	        }
	    }

	    return false;
	}


	@Override
	public boolean categoryAdd(String memberId, String categoryName) {
	    Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
	    if (optionalMember.isEmpty()) {
	        return false;
	    }

	    Member member = optionalMember.get();
	    Wishlist wishlist = new Wishlist(categoryName);
	    wishlist.setMember(member);

	    wishlistCategoryRepository.save(wishlist);
	    return true;
	}
	
	@Override
	public List<WishlistDTO> listByMemberId(String memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if (optionalMember.isEmpty()) {
            return List.of(); // 로그인 안 된 경우 빈 목록
        }

        Member member = optionalMember.get();
        List<Wishlist> wishlists = wishlistCategoryRepository.findByMember(member);
        return WishlistDTO.of(wishlists);
    }

}
