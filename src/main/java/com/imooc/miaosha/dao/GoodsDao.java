package com.imooc.miaosha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imooc.miaosha.domain.MiaoshaGoods;
import com.imooc.miaosha.vo.GoodsVo;

@Mapper
public interface GoodsDao {
	
	@Select("select g.*, m.miaosha_price, m.stock_count, m.start_date, m.end_date from miaosha_goods m left join goods g on m.goods_id = g.id")
	public List<GoodsVo> listGoodsVo();
	
	@Select("select g.*, m.miaosha_price, m.stock_count, m.start_date, m.end_date from miaosha_goods m left join goods g on m.goods_id = g.id where g.id = #{id}")
	public GoodsVo getGoodsVoById(@Param("id") Long goodsId);
	
	@Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
	public int reduceStock(MiaoshaGoods g);
}
