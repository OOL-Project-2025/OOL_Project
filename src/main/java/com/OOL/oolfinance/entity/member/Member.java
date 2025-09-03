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

    @Column(length = 40)
    private String password;

    @Column(length = 20)
    private String nickname;

    @Column
    private String profileImage;

    @Column
    private MemberStatus status;

    @Column
    private String provider;

    @Column
    private String providerId;

    @Column(name = "oauth2_access_token", columnDefinition = "CLOB")
    private String oauth2AccessToken;

    @Column
    private String accessToken;

    @Column
    private String refreshToken;

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Wishlist> category = new ArrayList<>();

    public void updateOauth2AccessToken(String oauth2AccessToken) { this.oauth2AccessToken = oauth2AccessToken;}

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

    public void updateAccessToken(String accessToken) { this.accessToken = accessToken; }

    public void updateRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public void updateStatus(MemberStatus status) {this.status = status; }
}
