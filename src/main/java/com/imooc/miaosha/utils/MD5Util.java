package com.imooc.miaosha.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
	
	public static String md5(String src) {
		return DigestUtils.md5Hex(src);
	}
	
	private static String salt = "1a2b3c4d";
	
	public static String inputPassToFormPass(String inputPass) {
		String formPass = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
		return md5(formPass);
	}
	
	public static String formPassToDBPass(String formPass, String saltDB) {
		String dbPass = "" + saltDB.charAt(0) + saltDB.charAt(2) + formPass + saltDB.charAt(5) + saltDB.charAt(4);
		return md5(dbPass);
	}
	
	public static String inputPassToDBPass(String inputPass, String saltDB) {
		String formPass = inputPassToFormPass(inputPass);
		return formPassToDBPass(formPass, saltDB);
	}
	
	public static void main(String[] args) {
//		String formPass = inputPassToFormPass("123456");
		String password = inputPassToDBPass("123456","1a2b3c4d");
		System.out.println(password);
	}
	
}
