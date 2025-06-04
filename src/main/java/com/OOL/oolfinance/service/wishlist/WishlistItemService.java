package com.OOL.oolfinance.service.wishlist;

import java.util.List;
import java.util.stream.Collectors;

import com.OOL.oolfinance.dto.stock.StockDTO;
import org.springframework.stereotype.Service;

import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.entity.stock.Stock;
import com.OOL.oolfinance.entity.wishlist.Wishlist;
import com.OOL.oolfinance.entity.wishlist.WishlistItem;
import com.OOL.oolfinance.repository.member.MemberRepository;  // 추가된 부분
import com.OOL.oolfinance.repository.stock.StockRepository;
import com.OOL.oolfinance.repository.wishlist.WishlistCategoryRepository;
import com.OOL.oolfinance.repository.wishlist.WishlistItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistItemService {

    private final WishlistCategoryRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final StockRepository stockRepository;
    private final MemberRepository memberRepository; // 추가된 부분
    
    // 회원의 위시리스트에 포함된 모든 주식 반환
    public List<Stock> getStocksInUserWishlist(String memberId) {
        if (memberId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        List<WishlistItem> items = wishlistItemRepository.findByWishlist_Member_MemberId(memberId);

        return items.stream()
                .map(WishlistItem::getStockInfo)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<StockDTO> getStockDTOsInUserWishlist(String memberId) {
        if (memberId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        List<WishlistItem> items = wishlistItemRepository.findByWishlist_Member_MemberId(memberId);

        return items.stream()
                .map(WishlistItem::transferStockInfoToStockDTO)
                .distinct()
                .collect(Collectors.toList());
    }

    // 위시리스트에 주식 추가
    public void addStockToWishlist(String memberId, Long wishlistId, String stockCode) {
        // 해당 회원을 DB에서 조회
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        // 해당 회원의 위시리스트를 조회
        List<Wishlist> wishlists = wishlistRepository.findByMember_MemberId(memberId);

        if (wishlists.isEmpty()) {
            // 위시리스트가 없으면 새로운 위시리스트를 생성
            Wishlist newWishlist = new Wishlist("기본 위시리스트");
            newWishlist.setMember(member); // 현재 로그인된 회원 객체를 설정
            wishlistRepository.save(newWishlist);

            // 새로 생성된 위시리스트의 id를 가져옵니다.
            wishlistId = newWishlist.getId();
        }

        // wishlistId가 null이거나 제공되지 않으면 첫 번째 위시리스트 사용
        if (wishlistId == null) {
            wishlistId = wishlists.get(0).getId(); // 첫 번째 위시리스트 선택
        }

        // 선택한 위시리스트를 DB에서 조회
        Wishlist wishlist = wishlistRepository.findByIdAndMember_MemberId(wishlistId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("위시리스트를 찾을 수 없습니다."));

        // 해당 주식 코드로 주식 찾기
        Stock stock = stockRepository.findByStockCode(stockCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 주식을 찾을 수 없습니다."));

        // 이미 해당 주식이 위시리스트에 있는지 확인
        boolean exists = wishlistItemRepository.existsByWishlistAndStockInfo(wishlist, stock);
        if (exists) {
            throw new IllegalArgumentException("이미 찜한 주식입니다.");
        }

        // 위시리스트에 주식 추가
        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setWishlist(wishlist);  // 위시리스트 설정
        wishlistItem.setStockInfo(stock);    // 주식 설정
        wishlistItemRepository.save(wishlistItem);
        
    }
}