package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;

public interface OrderService {
	
	MiaoshaOrder getMiaoshaOrderByUserIdAndGoodsId(long userId, long goodsId);
	
	OrderInfo createOrder(MiaoshaUser user, GoodsVo goods);
	
	OrderInfo getById(Long id);
}
