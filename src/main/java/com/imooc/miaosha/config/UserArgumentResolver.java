package com.imooc.miaosha.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.service.impl.MiaoshaUserServiceImpl;
import com.imooc.miaosha.utils.CookieUtil;

/**
 * 当传到服务端的参数中有MiaoshaUser时，
 * 会执行resolveArgument将值封装到MiaoshaUser中
 * @author aiqilong
 *
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver{
	
	@Autowired
	private MiaoshaUserService userService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		//判断类型是否为MiaoshaUser类型
		Class<?> clazz = parameter.getParameterType();
		return clazz == MiaoshaUser.class;
	}
	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		String paramToken = request.getParameter(MiaoshaUserServiceImpl.COOKIE_TOKEN_NAME);
		Cookie cookie = CookieUtil.get(request, MiaoshaUserServiceImpl.COOKIE_TOKEN_NAME);
		String cookieToken = null;
		if(cookie != null) {
			cookieToken = cookie.getValue();
		}
		if(StringUtils.isBlank(paramToken) && StringUtils.isBlank(cookieToken)){
			return null;
		}
		String token = StringUtils.isBlank(paramToken)?cookieToken:paramToken;
		MiaoshaUser user =userService.getByToken(response, token);
		return user;
	}
	
}
