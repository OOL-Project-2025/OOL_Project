package com.OOL.oolfinance.entity.wishlist;

import java.util.ArrayList;
import java.util.List;

import com.OOL.oolfinance.entity.member.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : Heart
 * @date : 2/20/25 / 2:22 PM
 * @modifyed : $
 **/

@Entity
@Getter
@NoArgsConstructor


public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String wishlistName;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishlistItem> wishlistItems= new ArrayList<>();
    
   
    // 생성자 추가 (Setter 없이 값을 설정하기 위해)
    public Wishlist(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    // Getter (Setter 없이 값 읽기만 가능)
    public String getWishlistName() {
        return wishlistName;
    }
    
    public void setMember(Member member) {
        this.member = member;
    }
    
    public Wishlist(String wishlistName, Member member) {
        this.wishlistName = wishlistName;
        this.member = member;
    }
}
