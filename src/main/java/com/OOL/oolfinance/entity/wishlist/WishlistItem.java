package com.OOL.oolfinance.entity.wishlist;

import com.OOL.oolfinance.entity.stock.Stock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : HeartListItem
 * @date : 2/20/25 / 2:39 PM
 * @modifyed : $
 **/

@Entity
@Getter
@NoArgsConstructor
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;

    @ManyToOne
    @JoinColumn(name = "stockCode")
    private Stock stockInfo;
}
