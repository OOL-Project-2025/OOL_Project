package com.OOL.oolfinance.controller.member;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionApiController {

	@GetMapping("/api/session/login-id")
    public String getLoginId(HttpSession session) {
        Object loginId = session.getAttribute("loginId");
        if (loginId != null) {
            return loginId.toString();
        } else {
            return "NO_SESSION";
        }
    }
}