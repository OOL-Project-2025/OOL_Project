package com.OOL.oolfinance.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : CustomUserDetails
 * @date : 2025. 9. 11. / 오후 11:28
 * @modifyed :
 **/

public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getNickname();
    }

    public String getProviderId() {
        return member.getProviderId();
    }

    public String getProvider() {
        return member.getProvider();
    }

    public String getAccessToken() {
        return member.getAccessToken();
    }

    public String getRefreshToken() {
        return member.getRefreshToken();
    }

    public String getProfileImage() {
        return member.getProfileImage();
    }
}
