package com.imooc.miaosha.service;

import java.util.List;

import com.imooc.miaosha.vo.GoodsVo;

public interface GoodsService {

	List<GoodsVo> listGoodsVo();
	
	GoodsVo getGoodsVoById(Long goodsId);

	void reduceStock(GoodsVo goods);
}
