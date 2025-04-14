package com.OOL.oolfinance.controller.wishlist;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.OOL.oolfinance.dto.WishlistDTO;
import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.service.wishlist.WishlistCategoryService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class WishlistCategoryController {
	
	private final WishlistCategoryService wishlistCategoryService;
	
	@GetMapping("/wishlist/category/inventory")
	public String wishlistInventory(Model model, Member parameter) {
		
		List<WishlistDTO> wishlists = wishlistCategoryService.list();
		model.addAttribute("wishlist",wishlists);
		
		return "wishlistCategory.html";
	}
	
	@PostMapping("/wishlist/category/do")
	public String wishlistAdd(HttpSession session, CategoryInput parameter) {
		String memberId = (String) session.getAttribute("loginId"); // 로그인한 사용자 아이디 가져오기
		if (memberId == null) {
			return "redirect:/member/login"; // 로그인 안되어 있으면 로그인 페이지로
		}

		wishlistCategoryService.categoryAdd(memberId,parameter.getCategoryName());
		return "wishlistCategory.html";
	}
	
	
}
