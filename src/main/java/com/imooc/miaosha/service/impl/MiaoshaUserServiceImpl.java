package com.imooc.miaosha.service.impl;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.miaosha.dao.MiaoshaUserDao;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.redis.MiaoshaUserPrefix;
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
	
	public MiaoshaUser getById(Long id) {
		//取缓存
		MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserPrefix.miaoshaUserPrefix, "" + id, MiaoshaUser.class);
		if(miaoshaUser != null) {
			return miaoshaUser;
		}
		//缓存取不到,查询数据库并加载到缓存中
		MiaoshaUser userDb = miaoshaUserDao.getById(id);
		if(userDb == null) {
			throw new GlobalException(CodeMsg.USER_NOT_EXIST);
		}
		redisService.set(MiaoshaUserPrefix.miaoshaUserPrefix, "" + id, userDb);
		return userDb;
	}
	
	public Boolean login(HttpServletResponse response, LoginVo loginVo) {
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
	
	@Transactional
	public boolean updatePassword(String token, Long id, String passwordNew, HttpServletResponse response) {
		MiaoshaUser user = getById(id);
		String passwdNewDb = MD5Util.formPassToDBPass(passwordNew, user.getSalt());
		//更新数据库
		MiaoshaUser updateUser = new MiaoshaUser();
		updateUser.setId(id);
		updateUser.setPassword(passwdNewDb);
		miaoshaUserDao.update(updateUser);
		//更新缓存
		redisService.delete(MiaoshaUserPrefix.miaoshaUserPrefix, "" + id);
		user.setPassword(passwdNewDb);
		redisService.set(TokenPrefix.tokenPrefix, "" + id, user);
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
