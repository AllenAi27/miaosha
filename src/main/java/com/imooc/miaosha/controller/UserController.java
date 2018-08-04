package com.imooc.miaosha.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.result.Result;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@RequestMapping("/info")
	@ResponseBody
	public Result<MiaoshaUser> info(MiaoshaUser user, Model model) {
		return Result.success(user);
		
	}
	
}
