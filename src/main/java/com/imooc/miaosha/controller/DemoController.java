package com.imooc.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.UserPrefix;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.UserService;

@Controller
@RequestMapping("/hello")
public class DemoController {
	
	@Autowired
	private UserService UserService;
	
	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/print")
	@ResponseBody
	public String demoPrint() {
		return "hello world!";
	}
	
	@RequestMapping("/success")
	@ResponseBody
	public Result<String> hello() {
		return Result.success("成功!");
	}
	
	@RequestMapping("/error")
	@ResponseBody
	public Result<String> error() {
		return Result.fail(CodeMsg.SERVER_ERROR);
	}

	//返回thymleaf
	@RequestMapping("/thymeleaf")
	public String thymeleaf(Model model) {
		model.addAttribute("name", "allen");
		return "hello";
	}
	
	@RequestMapping("/db/getUser")
	@ResponseBody
	public Result<User> getUserById(int id){
		User user = UserService.getById(id);
		return Result.success(user);
	}
	
	@RequestMapping("/redis/get")
	@ResponseBody
	public Result<User> getValueByRedis(){
		User value = redisService.get(UserPrefix.getById, "1", User.class);
		return Result.success(value);
	}
	
	@RequestMapping("/redis/set")
	@ResponseBody
	public Result<Boolean> setValueByRedis() {
		User user = new User();
		user.setId(1);
		user.setName("111");
		boolean flag = redisService.set(UserPrefix.getById ,"1", user);
		return Result.success(flag);
	}
}
