package com.OOL.oolfinance.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import com.OOL.oolfinance.entity.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
