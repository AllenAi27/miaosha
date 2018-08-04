package com.imooc.miaosha.redis;

public class GoodsPrefix extends BasePrefix{
	
	public GoodsPrefix(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	
	public GoodsPrefix(String prefix) {
		super(prefix);
	}

	public static GoodsPrefix goodsPrefix = new GoodsPrefix(10, "goods");
}
