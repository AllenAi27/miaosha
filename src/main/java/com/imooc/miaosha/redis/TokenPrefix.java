package com.imooc.miaosha.redis;

public class TokenPrefix extends BasePrefix{
	
	private static final int TOKEN_EXPIRE_SECONDS = 3600*24*2;

	public TokenPrefix(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	
	public TokenPrefix(String prefix) {
		super(prefix);
	}

	public static TokenPrefix tokenPrefix = new TokenPrefix(TOKEN_EXPIRE_SECONDS, "token");
}
