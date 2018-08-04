package com.imooc.miaosha.redis;

public class MiaoshaUserPrefix extends BasePrefix{
	
	public MiaoshaUserPrefix(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	
	public MiaoshaUserPrefix(String prefix) {
		super(prefix);
	}

	public static MiaoshaUserPrefix miaoshaUserPrefix = new MiaoshaUserPrefix(0, "miaoshaUser");
}
