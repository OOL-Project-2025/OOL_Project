package com.OOL.oolfinance.repository.member;

import com.OOL.oolfinance.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : MemberRepository
 * @date : 3/4/25 / 11:13 PM
 * @modifyed : $
 **/

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    boolean existsByNickname(String nickname);
}
