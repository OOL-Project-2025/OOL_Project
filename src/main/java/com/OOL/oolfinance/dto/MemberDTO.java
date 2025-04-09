package com.OOL.oolfinance.dto;

import com.OOL.oolfinance.entity.member.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
	private String memberId;
	private String memberPassword;
	private String memberNickname;
	
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
