package com.OOL.oolfinance.dto;

import com.OOL.oolfinance.entity.member.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(name = "MemberDTO", description = "유저 정보 DTO")
public class MemberDTO {

    @Schema(name = "memberId", type = "String", description = "유저 id")
    private String providerId;

    @Schema(name = "memberPassword", type = "String", description = "유저 password")
    private String memberPassword;

    @Schema(name = "memberNickname", type = "String", description = "유저 닉네임")
    private String memberNickname;

    private String provider;

    public void setMemberId(String memberId) {
        this.providerId = memberId;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public static MemberDTO toMemberDTO(Member member) {
        return new MemberDTO(
                member.getMemberId(),
                member.getPassword(),
                member.getNickname(),
                member.getProvider()
        );
    }

    public Member toEntity() {
        return Member.builder()
                .memberId(this.providerId)
                .password(this.memberPassword)
                .nickname(this.memberNickname)
                .build();
    }

}
