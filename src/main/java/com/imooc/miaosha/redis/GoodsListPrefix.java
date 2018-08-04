package com.imooc.miaosha.redis;

public class GoodsListPrefix extends BasePrefix{
	
	public GoodsListPrefix(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	
	public GoodsListPrefix(String prefix) {
		super(prefix);
	}

	public static GoodsListPrefix goodsListPrefix = new GoodsListPrefix(60, "goodsList");
}
