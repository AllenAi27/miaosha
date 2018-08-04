package com.imooc.miaosha.service;

import javax.servlet.http.HttpServletResponse;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.vo.LoginVo;

public interface MiaoshaUserService {

	Boolean login(HttpServletResponse response, LoginVo loginVo);
	
	MiaoshaUser getByToken(HttpServletResponse response, String token);
}
