package com.imooc.miaosha.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ValidateUtil {
	
	private final static Pattern pattern = Pattern.compile("1\\d{10}");
	
	public static boolean mobileValidate(String mobile) {
		if(StringUtils.isBlank(mobile)) {
			return false;
		}else {
			Matcher m = pattern.matcher(mobile);
			return m.matches();
		}
	}
}
