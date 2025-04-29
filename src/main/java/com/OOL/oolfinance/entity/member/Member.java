package com.OOL.oolfinance.entity.member;

import java.util.ArrayList;
import java.util.List;

import com.OOL.oolfinance.dto.MemberDTO;
import com.OOL.oolfinance.entity.wishlist.Wishlist;
import com.OOL.oolfinance.enums.MemberStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40, nullable = false, unique = true)
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

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateMemberStatus(MemberStatus status) {
        this.status = status;
    }
    
    public static Member toUpdateMemberEntity(MemberDTO memberDTO) {
        return Member.builder()
                .memberId(memberDTO.getMemberId())
                .password(memberDTO.getMemberPassword())
                .nickname(memberDTO.getMemberNickname())
                .build();
    }

	public void setMemberId(String memberId) {
		
	}
}
