package com.imooc.miaosha.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imooc.miaosha.domain.MiaoshaUser;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	@RequestMapping("/to_goodsList")
	public String toGoodsList(MiaoshaUser user, Model model) {
		if(user == null) {
			return "login";
		}
		model.addAttribute("user", user);
		return "goods_list";
		
	}
}
