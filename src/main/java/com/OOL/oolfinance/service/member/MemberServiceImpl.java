package com.OOL.oolfinance.service.member;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.OOL.oolfinance.dto.MemberDTO;
import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.enums.MemberStatus;
import com.OOL.oolfinance.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : MemberServiceImpl
 * @date : 3/5/25 / 10:59 PM
 * @modifyed : $
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Value("${custom.path}")
    private String fileDir;

    @Override
    @Transactional
    public void setProfile(Long id, MultipartFile profilePhoto, String nickname) {
        Member member = memberRepository.findById(id).orElseThrow(() -> {return new IllegalArgumentException("찾을 수 없는 id입니다.");});

        String photoName = null;

        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            if (member.getProfileImage() != null) {
                deleteImage(member.getProfileImage());
            }
            photoName = saveImage(profilePhoto);
            member.updateProfileImage(photoName);
        }
        if (nickname != member.getNickname()) {
            member.updateNickname(nickname);
        }
    }

    @Override
    public boolean validateDuplicateUserNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Override
    public String saveImage(MultipartFile file) {
        log.info(file.getContentType());
        if (!file.getContentType().startsWith("image")) {
            throw new IllegalArgumentException("이미지 파일이 아닙니다.");
        }

        String originalName = file.getOriginalFilename();
        String imageName = UUID.randomUUID().toString() + originalName.substring(originalName.lastIndexOf("."));

        String saveName = fileDir + File.separator + imageName;
        Path savePath = Paths.get(saveName);

        try {
            file.transferTo(savePath);

            String thumbnailSaveName = fileDir + File.separator + "thumb_" + imageName;
            File thumbnailFile = new File(thumbnailSaveName);
            Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile,50, 50);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageName;
    }

    @Override
    public void deleteImage(String image) {
        File removeFile = null;

        try {
            removeFile = new File(fileDir + File.separator + URLDecoder.decode("thumb_" + image, "UTF-8"));
            removeFile.delete();
            removeFile = new File(fileDir + File.separator + URLDecoder.decode(image, "UTF-8"));
            removeFile.delete();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void updateMemberStatus(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없음."));

        member.updateMemberStatus(MemberStatus.DEACTIVATED);
    }

    @Override
    public void signup(MemberDTO memberDTO) {
        //1. dto -> entity 변환
        //2. repository의 save 메서드 호출
        Member member = Member.builder()
                        .memberId(memberDTO.getMemberId())
                        .password(memberDTO.getMemberPassword())
                        .nickname(memberDTO.getMemberNickname())
                        .build();
        memberRepository.save(member);
        //repository의 save 메서드 호출 (조건. entity 객체를 넘겨줘야 함.)
    }
    
    @Override
    public MemberDTO login(MemberDTO memberDTO) {
    	/*
    	 * 1. 회원 아이디 DB 조회
    	 * 2. DB 조회 비밀번호와 사용자 입력 비밀번호가 일치하는지 판단 */
    	
    	Optional<Member> byMemberId = memberRepository.findByMemberId(memberDTO.getMemberId());
    	if(byMemberId.isPresent()) {
    		//조회 결과 존재
    		Member member = byMemberId.get();
    		
    		if (member.getPassword().equals(memberDTO.getMemberPassword())) {
    			//비밀번호 일치
    			//entity -> dto 변환 후 리턴
    			MemberDTO memDTO = MemberDTO.toMemberDTO(member);
    			return memDTO;
    			
    		}
    	} else {
    		//조회 결과 없음.
    		return null;
    	}
		return null;
    }
    
    public MemberDTO updateForm(String myId) {
    	Optional<Member> optionalMemberEntity = memberRepository.findByMemberId(myId);
    	if(optionalMemberEntity.isPresent()) {
    		return MemberDTO.toMemberDTO(optionalMemberEntity.get());
    	} else {
    		return null;
    	}
    }
    
    @Transactional
    public void memberUpdate(MemberDTO memberDTO) {
        Member member = memberRepository.findByMemberId(memberDTO.getMemberId())
            .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        member.updatePassword(memberDTO.getMemberPassword());
        member.updateNickname(memberDTO.getMemberNickname());
    }
}
