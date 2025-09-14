package com.OOL.oolfinance.service.member;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import com.OOL.oolfinance.dto.MyPageDTO;
import com.OOL.oolfinance.dto.token.GeneratedToken;
import com.OOL.oolfinance.oauth2user.OAuth2Provider;
import com.OOL.oolfinance.oauth2user.OAuth2UserUnlinkManager;
import com.OOL.oolfinance.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.OOL.oolfinance.dto.MemberDTO;
import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.enums.MemberStatus;
import com.OOL.oolfinance.repository.member.MemberRepository;
import com.OOL.oolfinance.repository.wishlist.WishlistCategoryRepository;

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
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final WishlistCategoryRepository wishlistCategoryRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final OAuth2UserUnlinkManager oAuth2UserUnlinkManager;

    @Value("${custom.path}")
    private String fileDir;

    @Override
    public void setProfile(Member member, MultipartFile profilePhoto, String nickname) {

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
        memberRepository.save(member);
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
            Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 50, 50);


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
                .password(memberDTO.getMemberPassword())
                .nickname(memberDTO.getMemberNickname())
                .provider(memberDTO.getProvider())
                .providerId(memberDTO.getProviderId())
                .status(MemberStatus.ACTIVE)
                .build();
        Member Member = memberRepository.save(member);

//        Wishlist defaultWishlist = new Wishlist("기본", Member);
//        wishlistCategoryRepository.save(defaultWishlist);
    }

    @Override
    public Member login(MemberDTO memberDTO) {
        /*
         * 1. 회원 아이디 DB 조회
         * 2. DB 조회 비밀번호와 사용자 입력 비밀번호가 일치하는지 판단 */

        Optional<Member> byMemberId = memberRepository.findByProviderAndProviderId(memberDTO.getProvider(), memberDTO.getProviderId());
        if (byMemberId.isPresent()) {
            //조회 결과 존재
            Member member = byMemberId.get();

            if (member.getStatus().equals(MemberStatus.DEACTIVATED)) {
                return null;
            }

            if (member.getProvider().equals("form") && member.getPassword().equals(memberDTO.getMemberPassword())) {
                //비밀번호 일치
                GeneratedToken generatedToken = jwtTokenUtils.generatedToken(member.getProviderId(), member.getProvider());
                member.updateAccessToken(generatedToken.getAccessToken());
                member.updateRefreshToken(generatedToken.getRefreshToken());
                //entity -> dto 변환 후 리턴
                memberRepository.save(member);
                return member;
            }
        } else {
            //조회 결과 없음.
            return null;
        }
        return null;
    }

    public MyPageDTO updateForm(Member member) {
        if (member != null) {
            String imageUrl = "/images/" + member.getProfileImage();

            return MyPageDTO.builder()
                    .memberNickname(member.getNickname())
                    .providerId(member.getProviderId())
                    .memberPassword(member.getPassword())
                    .provider(member.getProvider())
                    .image(imageUrl)
                    .build();
        } else {
            return null;
        }
    }

    @Transactional
    public void memberUpdate(MemberDTO memberDTO, Member member) {
        member.updatePassword(memberDTO.getMemberPassword());
        member.updateNickname(memberDTO.getMemberNickname());
        memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(Member member) {
        member.updateMemberStatus(MemberStatus.DEACTIVATED);

        if (!member.getProvider().equals("form")) {
            OAuth2Provider provider = OAuth2Provider.valueOf(member.getProvider().toUpperCase());
            log.info(String.valueOf(provider));
            oAuth2UserUnlinkManager.unlink(provider, member.getOauth2AccessToken());
        }

        memberRepository.save(member);
    }

    @Override
    public void updateAccessToken(Member member) {
        member.updateAccessToken(null);
        memberRepository.save(member);
    }

}
