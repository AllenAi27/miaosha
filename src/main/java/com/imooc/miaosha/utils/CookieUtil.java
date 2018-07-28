package com.imooc.miaosha.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static void set(HttpServletResponse response, String key,
						String value, int maxAge) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	public static Cookie get(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if(cookieName.equals(key)) {
					return cookie;
				}
			}
		}
		return null;
	}
}
