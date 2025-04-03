package com.OOL.oolfinance.repository.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;
import com.OOL.oolfinance.entity.wishlist.Wishlist;

public interface WishlistCategoryRepository extends JpaRepository<Wishlist, Long> {

}
