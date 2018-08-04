package com.imooc.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MiaoshaService miaoshaService;
	
	/**
	 * QPS 357.5
	 * 1000*10
	 * @param goodsId
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
	@ResponseBody
	public Result<OrderInfo> doMiaosha(@RequestParam("goodsId") Long goodsId, MiaoshaUser user, Model model) {
		if(user == null) {
			return Result.fail(CodeMsg.NOT_LOGIN);
		}
		//查询库存
		GoodsVo goodsVo = goodsService.getGoodsVoById(goodsId);
		int stockCount = goodsVo.getStockCount();
		if(stockCount <= 0) {
			return Result.fail(CodeMsg.MIAO_SHA_STOCK_EMPTY);
		}
		//判断是否已经秒杀过
		MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(), goodsVo.getId());
		if(miaoshaOrder != null) {
			return Result.fail(CodeMsg.MIAO_SHA_REPEAT_ACTION);
		}
		//下订单 减库存 写入秒杀订单表
		OrderInfo orderInfo = miaoshaService.miaosha(user,goodsVo);
		return Result.success(orderInfo);
	}
	
}
