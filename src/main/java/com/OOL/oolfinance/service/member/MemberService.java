package com.OOL.oolfinance.service.member;

import org.springframework.stereotype.Service;
import com.OOL.oolfinance.dto.MemberDTO;
import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository memberRepository;

	public void save(MemberDTO memberDTO) {
		//1. dto -> entity 변환
		//2. repository의 save 메서드 호출
		Member member = Member.toMember(memberDTO);
		memberRepository.save(member);
		//repository의 save 메서드 호출 (조건. entity 객체를 넘겨줘야 함.)
		
	}
	
}
