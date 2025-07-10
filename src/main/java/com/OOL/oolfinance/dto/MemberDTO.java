package com.OOL.oolfinance.dto;

import com.OOL.oolfinance.entity.member.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(name = "MemberDTO", description = "유저 정보 DTO")
public class MemberDTO {

	@Schema(name = "memberId", type = "String", description = "유저 id")
	private String memberId;

	@Schema(name = "memberPassword", type = "String", description = "유저 password")
	private String memberPassword;

	@Schema(name = "memberNickname", type = "String", description = "유저 닉네임")
	private String memberNickname;
	
	public void setMemberId(String memberId) {
		this.memberId = memberId;
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
            member.getNickname()
        );
	}
	
	public Member toEntity() {
	    return Member.builder()
	            .memberId(this.memberId)
	            .password(this.memberPassword)
	            .nickname(this.memberNickname)
	            .build();
	}

}
