package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;

public interface MiaoshaService {

	OrderInfo miaosha(MiaoshaUser user, GoodsVo goods);
}
