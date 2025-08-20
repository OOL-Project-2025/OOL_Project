package com.OOL.oolfinance.controller.member;

import java.util.HashMap;
import java.util.Map;

import com.OOL.oolfinance.dto.MemberDTO;
import com.OOL.oolfinance.dto.general.GeneralResponse;
import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.enums.StatusEnum;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SessionApiController {

	@GetMapping("/api/session/login-id")
    public ResponseEntity<GeneralResponse<MemberDTO>> getLoginId(@AuthenticationPrincipal Member member) {
        String loginId = member.getProviderId();
        String nickname = member.getNickname();
        String password = member.getPassword();
        String platform = member.getProvider();

        log.info(loginId);
        log.info(nickname);
        log.info(password);
        log.info(platform);

        MemberDTO memberDTO = MemberDTO.builder()
                .providerId("NO_SESSION")
                .build();

        if (loginId != null) {
            memberDTO = MemberDTO.builder()
                    .memberNickname(nickname)
                    .memberPassword(password)
                    .provider(platform)
                    .providerId(loginId)
                    .build();

            return ResponseEntity.ok(GeneralResponse.<MemberDTO>builder()
                            .status(StatusEnum.OK)
                            .message("success")
                            .data(memberDTO)
                    .build());
        } else {
            return ResponseEntity.ok(GeneralResponse.<MemberDTO>builder()
                    .status(StatusEnum.UNAUTHORIZED)
                    .message("null")
                    .data(memberDTO)
                    .build());
        }
    }
}