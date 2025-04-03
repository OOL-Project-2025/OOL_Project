package com.OOL.oolfinance.dto;

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
}
