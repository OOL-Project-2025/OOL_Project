package com.OOL.oolfinance.service.member;

import com.OOL.oolfinance.dto.MemberDTO;
import com.OOL.oolfinance.entity.member.Member;
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
    void setProfile(Member member, MultipartFile profilePhoto, String nickname);

    boolean validateDuplicateUserNickname(String nickname);

    String saveImage(MultipartFile file);

    void deleteImage(String image);

    @Transactional
    void updateMemberStatus(Long id);

    void signup(MemberDTO memberDTO);

	Member login(MemberDTO memberDTO);

	MemberDTO updateForm(String myId);

	void memberUpdate(MemberDTO memberDTO);

	void deleteMember(Member member);


    @Transactional
    void updateAccessToken(Member member);
}
