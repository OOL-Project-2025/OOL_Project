package com.OOL.oolfinance.repository.wishlist;

import com.OOL.oolfinance.entity.stock.Stock;
import com.OOL.oolfinance.entity.wishlist.Wishlist;
import com.OOL.oolfinance.entity.wishlist.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    
    // 회원의 memberId로 WishlistItem을 찾는 메소드
    public List<WishlistItem> findByWishlist_Member_MemberId(String memberId);

	public boolean existsByWishlistAndStockInfo(Wishlist wishlist, Stock stock);
}
