package com.OOL.oolfinance.repository.wishlist;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.entity.wishlist.Wishlist;

public interface WishlistCategoryRepository extends JpaRepository<Wishlist, Long> {
	List<Wishlist> findByMember(Member member);
	Optional<Wishlist> findByIdAndMember_MemberId(Long id, String memberId);
	List<Wishlist> findByMember_MemberId(String memberId);
}
