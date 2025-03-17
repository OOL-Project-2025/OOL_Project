package com.OOL.oolfinance.service.member;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : MemberService
 * @date : 3/5/25 / 10:59 PM
 * @modifyed : $
 **/
public interface MemberService {

    @Transactional
    void setProfile(Long id, MultipartFile profilePhoto, String nickname);

    boolean validateDuplicateUserNickname(String nickname);

    String saveImage(MultipartFile file);

    void deleteImage(String image);

    @Transactional
    void updateMemberStatus(Long id);
    
    public void save(MemberDTO memberDTO) {
      //1. dto -> entity 변환
      //2. repository의 save 메서드 호출
      Member member = Member.toMember(memberDTO);
      memberRepository.save(member);
      //repository의 save 메서드 호출 (조건. entity 객체를 넘겨줘야 함.)

    }
}
