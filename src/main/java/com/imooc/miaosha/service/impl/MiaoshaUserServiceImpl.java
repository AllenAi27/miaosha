package com.imooc.miaosha.service.impl;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.miaosha.dao.MiaoshaUserDao;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.TokenPrefix;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.utils.CookieUtil;
import com.imooc.miaosha.utils.MD5Util;
import com.imooc.miaosha.utils.UUIdUtil;
import com.imooc.miaosha.vo.LoginVo;

@Service
public class MiaoshaUserServiceImpl implements MiaoshaUserService{
	
	public static final String COOKIE_TOKEN_NAME = "token";
	
	@Autowired
	private MiaoshaUserDao miaoshaUserDao;
	
	@Autowired
	private RedisService redisService;
	
	public boolean login(HttpServletResponse response, LoginVo loginVo) {
		String mobile = loginVo.getMobile();
		String inputPass = loginVo.getPassword();
		MiaoshaUser user = miaoshaUserDao.getById(Long.parseLong(mobile));
		if(user == null) {
			throw new GlobalException(CodeMsg.USER_NOT_EXIST);
		}
		String dbPass = user.getPassword();
		String dbSalt = user.getSalt();
		String formPassToDB = MD5Util.formPassToDBPass(inputPass, dbSalt);
		if(!formPassToDB.equals(dbPass)) {
			throw new GlobalException(CodeMsg.LOGIN_ERROR);
		}
		String tokenVal = UUIdUtil.uuid();
		addCookie(response, tokenVal, user);
		return true;
	}
	
	public MiaoshaUser getByToken(HttpServletResponse response, String token) {
		if(StringUtils.isBlank(token)) {
			return null;
		}
		MiaoshaUser user = redisService.get(TokenPrefix.tokenPrefix, token, MiaoshaUser.class);
		if(user == null) {
			return null;
		}
		addCookie(response, token, user);
		return user;
	}
	
	private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
		redisService.set(TokenPrefix.tokenPrefix , token, user);
		CookieUtil.set(response, COOKIE_TOKEN_NAME, token, TokenPrefix.tokenPrefix.expireSeconds());
	}
	
}
