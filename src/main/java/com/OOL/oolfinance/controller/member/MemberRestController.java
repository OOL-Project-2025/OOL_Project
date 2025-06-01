package com.OOL.oolfinance.controller.member;

import com.OOL.oolfinance.dto.general.GeneralResponse;
import com.OOL.oolfinance.enums.StatusEnum;
import com.OOL.oolfinance.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping(value = "/api/mypage/profile")
    public ResponseEntity<GeneralResponse> setMemberProfile(@RequestPart(value = "id") Long id,
                                                            @RequestPart(value = "profilePhoto") MultipartFile profilePhoto,
                                                            @RequestPart(value = "nickname") String nickname) {
        memberService.setProfile(id, profilePhoto, nickname);
        GeneralResponse response = GeneralResponse.builder()
                .status(StatusEnum.OK)
                .message("success")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/api/mypage/delete/{id}")
    public ResponseEntity<GeneralResponse> deleteMember(@RequestParam(value = "id") Long id) {
        memberService.updateMemberStatus(id);
        GeneralResponse response = GeneralResponse.builder()
                .status(StatusEnum.OK)
                .message("success")
                .build();
        return ResponseEntity.ok(response);
    }
}
