package com.imooc.miaosha.redis;

public class OrderPrefix extends BasePrefix{
	
	public OrderPrefix(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	
	public OrderPrefix(String prefix) {
		super(prefix);
	}

	public static OrderPrefix goodsPrefix = new OrderPrefix("MiaoshaOrder");
}
