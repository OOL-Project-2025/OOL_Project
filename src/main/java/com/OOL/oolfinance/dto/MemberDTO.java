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

    @Schema(name = "providerId", type = "String", description = "유저 id")
    private String providerId;

    @Schema(name = "memberPassword", type = "String", description = "유저 password")
    private String memberPassword;

    @Schema(name = "memberNickname", type = "String", description = "유저 닉네임")
    private String memberNickname;

    @Schema(name = "provider", type = "String", description = "플랫폼")
    private String provider;

    public void setMemberId(String memberId) {
        this.providerId = memberId;
    }

    public static MemberDTO toMemberDTO(Member member) {
        return new MemberDTO(
                member.getMemberId(),
                member.getPassword(),
                member.getNickname(),
                member.getProvider()
        );
    }
}
