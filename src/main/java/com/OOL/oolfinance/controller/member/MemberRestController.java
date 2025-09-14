package com.OOL.oolfinance.controller.member;

import com.OOL.oolfinance.dto.MemberDTO;
import com.OOL.oolfinance.dto.MyPageDTO;
import com.OOL.oolfinance.dto.general.GeneralResponse;
import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.enums.StatusEnum;
import com.OOL.oolfinance.service.member.MemberService;
import com.OOL.oolfinance.util.CookieUtils;
import com.OOL.oolfinance.util.JwtCookieUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

@Slf4j
@RequestMapping(value = "/api/mypage")
@RestController
@RequiredArgsConstructor
@Tag(name = "Member", description = "유저 데이터 관련 API")
public class MemberRestController {
    private final MemberService memberService;
    private final JwtCookieUtils jwtCookieUtils;

    @GetMapping("/profile")
    @ResponseBody
    @Operation(summary = "유저 정보 가져오기", description = "유저 정보 업데이트하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업데이트 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "fail", content = @Content(mediaType = "application/json"))
    })
    public MyPageDTO getUpdateMember(@AuthenticationPrincipal Member member) {
        return memberService.updateForm(member);
    }

    @PostMapping(value = "/profile/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "유저 설정 변경", description = "닉네임, 프로필 사진을 변경하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "fail", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<GeneralResponse> setMemberProfile(@AuthenticationPrincipal Member member,
                                                            @RequestPart(value = "profilePhoto") MultipartFile profilePhoto,
                                                            @RequestPart(value = "nickname") String nickname) {
        memberService.setProfile(member, profilePhoto, nickname);
        GeneralResponse response = GeneralResponse.builder()
                .status(StatusEnum.OK)
                .message("success")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile/info")
    @Operation(summary = "유저 정보 수정", description = "유저 정보 업데이트하는 API", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = {
                    @ExampleObject(name = "someExample1", value = """ 
                                { 
                                    "memberId" : "someValue1", 
                                    "memberPassword" : "someValue2", 
                                    "memberNickname" : "someValue3"
                                } 
                            """)
            }
    )))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업데이트 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "fail", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> memberUpdate(@RequestBody MemberDTO memberDTO, @AuthenticationPrincipal Member member) {
        memberService.memberUpdate(memberDTO, member);
        return ResponseEntity.ok("업데이트 성공");
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "회원탈퇴", description = "회원탈퇴를 하기 위한 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "fail", content = @Content(mediaType = "application/json"))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "유저 id", example = "ool0000"),
    })
    public ResponseEntity<GeneralResponse> deleteMember(HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal Member member) {
        memberService.deleteMember(member);

        SecurityContextHolder.clearContext();
        jwtCookieUtils.deleteJwtCookies(request, response);

        GeneralResponse res = GeneralResponse.builder()
                .status(StatusEnum.OK)
                .message("success")
                .build();
        return ResponseEntity.ok(res);
    }
}
