package com.OOL.oolfinance.controller.member;

import java.util.HashMap;
import java.util.Map;

import com.OOL.oolfinance.dto.general.GeneralResponse;
import com.OOL.oolfinance.enums.StatusEnum;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionApiController {

	@GetMapping("/api/session/login-id")
    public ResponseEntity<GeneralResponse<String>> getLoginId(HttpSession session) {
        Object loginId = session.getAttribute("loginId");
        if (loginId != null) {
            return ResponseEntity.ok(GeneralResponse.<String>builder()
                            .status(StatusEnum.OK)
                            .message("success")
                            .data(loginId.toString())
                    .build());
        } else {
            return ResponseEntity.ok(GeneralResponse.<String>builder()
                    .status(StatusEnum.OK)
                    .message("success")
                    .data("NO_SESSION")
                    .build());
        }
    }
}