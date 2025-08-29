package com.OOL.oolfinance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	//기본페이지 요청 메서드
	@GetMapping("/")
    public String index() {
        return "redirect:/index.html";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "redirect:/main.html";
    }
}
