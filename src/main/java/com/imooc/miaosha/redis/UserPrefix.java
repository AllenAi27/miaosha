package com.imooc.miaosha.redis;

public class UserPrefix extends BasePrefix{

	public UserPrefix(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

	public UserPrefix(String prefix) {
		super(prefix);
	}
	
	public static UserPrefix getById = new UserPrefix("id");
	
	public static UserPrefix getByName = new UserPrefix("name");
}
