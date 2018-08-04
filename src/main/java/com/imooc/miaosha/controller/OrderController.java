package com.imooc.miaosha.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;
import com.imooc.miaosha.vo.OrderDetailVo;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private GoodsService goodsService;
	
	@RequestMapping("/detail")
	@ResponseBody
	public Result<OrderDetailVo> detail(MiaoshaUser user, @RequestParam("orderId") Long orderId){
		if(user == null) {
			return Result.fail(CodeMsg.NOT_LOGIN);
		}
		OrderInfo orderInfo = orderService.getById(orderId);
		if(orderInfo == null) {
			return Result.fail(CodeMsg.ORDER_NOT_EXIST);
		}
		GoodsVo goodsVo = goodsService.getGoodsVoById(orderInfo.getGoodsId());
		if(goodsVo == null) {
			return Result.fail(CodeMsg.SERVER_ERROR);
		}
		OrderDetailVo orderDetailVo = new OrderDetailVo();
		orderDetailVo.setGoodsVo(goodsVo);
		orderDetailVo.setOrderInfo(orderInfo);
		return Result.success(orderDetailVo);
	}
}
