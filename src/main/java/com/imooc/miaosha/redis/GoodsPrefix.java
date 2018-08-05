package com.imooc.miaosha.redis;

public class GoodsPrefix extends BasePrefix{
	
	public GoodsPrefix(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	
	public GoodsPrefix(String prefix) {
		super(prefix);
	}

	public static GoodsPrefix miaoshaGoodsPrefix = new GoodsPrefix(10, "msGoods");
	public static GoodsPrefix miaoshaGoodsStockPrefix = new GoodsPrefix(0, "msGoodsStock");
}
