package com.OOL.oolfinance.controller.wishlist;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.OOL.oolfinance.dto.WishlistDTO;
import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.service.wishlist.WishlistCategoryService;

import org.springframework.ui.Model;
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
	public String wishlistAdd(Model model, CategoryInput parameter) {
		
		boolean result = wishlistCategoryService.categoryAdd(parameter.getCategoryName());
		 
		return "redirect:/wishlistCategory.html";
	}
}
