package com.imooc.miaosha.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.GoodsListPrefix;
import com.imooc.miaosha.redis.GoodsPrefix;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.vo.GoodsDetailVo;
import com.imooc.miaosha.vo.GoodsVo;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private ThymeleafViewResolver thymeleafViewResolver;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	/**
	 * QPS 596.7
	 * 1000*10
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/to_goodsList", produces = "text/html")
	@ResponseBody
	public String toGoodsList(MiaoshaUser user, Model model, HttpServletResponse response, HttpServletRequest request) {
		if(user == null) {
			try {
				response.sendRedirect("/login/to_login");
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//取缓存
		String html = redisService.get(GoodsListPrefix.goodsListPrefix, "", String.class);
		if(!StringUtils.isBlank(html)) {
			return html;
		}
		List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
		model.addAttribute("goodsList", goodsVoList);
		model.addAttribute("user", user);
		//若缓存没有，则手动渲染，并存入缓存
		SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
		html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
		if(StringUtils.isNotBlank(html)) {
			redisService.set(GoodsListPrefix.goodsListPrefix, "", html);
		}
		return html;
	}
	
	@RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
	@ResponseBody
	public String toDetail(@PathVariable("goodsId") Long goodsId, Model model,
			MiaoshaUser user, HttpServletResponse response, HttpServletRequest request) {
		if(user == null) {
			try {
				response.sendRedirect("/login/to_login");
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//取缓存
		String html = redisService.get(GoodsPrefix.goodsPrefix, "" + goodsId, String.class);
		if(!StringUtils.isBlank(html)) {
			return html;
		}
		Long currentTime = System.currentTimeMillis();
		GoodsVo goodsVo = goodsService.getGoodsVoById(goodsId);
		Long startTime = goodsVo.getStartDate().getTime();
		Long endTime = goodsVo.getEndDate().getTime();
		int remainSeconds = 0;
		int miaoshaStatus = 0;
		if(currentTime < startTime) {
			miaoshaStatus = 0;
			remainSeconds = (int) ((startTime - currentTime)/1000);
		}else if(currentTime > endTime) {
			miaoshaStatus = 2;
			remainSeconds = -1;
		}else {
			miaoshaStatus = 1;
			remainSeconds = 0;
		}
		model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);
		model.addAttribute("goods", goodsVo);
		model.addAttribute("user",user);
		//若缓存没有，则手动渲染，并存入缓存
		SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
		html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
		if(StringUtils.isNotBlank(html)) {
			redisService.set(GoodsPrefix.goodsPrefix, "" + goodsId, html);
		}
		return html;
	}
	
	@RequestMapping("/detail/{goodsId}")
	@ResponseBody
	public Result<GoodsDetailVo> detail(@PathVariable("goodsId") Long goodsId, Model model,
			MiaoshaUser user, HttpServletResponse response, HttpServletRequest request) {
		if(user == null) {
			try {
				response.sendRedirect("/login/to_login");
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Long currentTime = System.currentTimeMillis();
		GoodsVo goodsVo = goodsService.getGoodsVoById(goodsId);
		Long startTime = goodsVo.getStartDate().getTime();
		Long endTime = goodsVo.getEndDate().getTime();
		int remainSeconds = 0;
		int miaoshaStatus = 0;
		if(currentTime < startTime) {
			miaoshaStatus = 0;
			remainSeconds = (int) ((startTime - currentTime)/1000);
		}else if(currentTime > endTime) {
			miaoshaStatus = 2;
			remainSeconds = -1;
		}else {
			miaoshaStatus = 1;
			remainSeconds = 0;
		}
		model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);
		model.addAttribute("goods", goodsVo);
		model.addAttribute("user",user);
		GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
		goodsDetailVo.setMiaoshaStatus(miaoshaStatus);
		goodsDetailVo.setRemainSeconds(remainSeconds);
		goodsDetailVo.setMiaoshaUser(user);
		goodsDetailVo.setGoodsVo(goodsVo);
		return Result.success(goodsDetailVo);
	}
}
