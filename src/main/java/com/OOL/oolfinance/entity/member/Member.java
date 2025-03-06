package com.OOL.oolfinance.entity.member;

import com.OOL.oolfinance.entity.wishlist.Wishlist;
import com.OOL.oolfinance.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : Member
 * @date : 2/17/25 / 10:00 PM
 * @modifyed : $
 **/

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40, nullable = false)
    private String memberId;

    @Column(length = 40, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String nickname;

    @Column
    private String profileImage;

    @Column
    private MemberStatus status;

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Wishlist> category = new ArrayList<>();
}
