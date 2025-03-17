package com.OOL.oolfinance.controller.member;

import com.OOL.oolfinance.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : MemberController
 * @date : 3/5/25 / 11:12 PM
 * @modifyed : $
 **/

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberService memberService;

    @PostMapping(value = "/api/mypage/profile")
    public String setMemberProfile(@RequestPart(value = "id") Long id,
                                @RequestPart(value = "profilePhoto") MultipartFile profilePhoto,
                                @RequestPart(value = "nickname") String nickname) {
        memberService.setProfile(id, profilePhoto, nickname);

        return "success";
    }

    @PostMapping(value = "/api/mypage/delete/{id}")
    public String deleteMember(@RequestParam(value = "id") Long id) {
        memberService.updateMemberStatus(id);
        return "success";
    }
}
