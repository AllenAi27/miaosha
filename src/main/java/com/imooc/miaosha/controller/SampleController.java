package com.imooc.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha.rabbitmq.MQSender;
import com.imooc.miaosha.result.Result;

@Controller
@RequestMapping("/hello")
public class SampleController {
	
	@Autowired
	private MQSender sender;
	
	@RequestMapping("/mq")
	@ResponseBody
	public Result<String> mq(){
		sender.send("hello,rabbitMQ!");
		return Result.success("发送消息至MQ成功！");
	}
	
	@RequestMapping("/mq/topic")
	@ResponseBody
	public Result<String> mqTopic(){
		sender.sendTopic("hello,rabbitMQ topic!");
		return Result.success("发送消息至MQ成功！");
	}
	
	@RequestMapping("/mq/fanout")
	@ResponseBody
	public Result<String> mqFanout(){
		sender.sendFanout("hello,rabbitMQ fanout!");
		return Result.success("发送消息至MQ成功！");
	}
	
	@RequestMapping("/mq/headers")
	@ResponseBody
	public Result<String> mqHeaders(){
		sender.sendHeaders("hello,rabbitMQ headers!");
		return Result.success("发送消息至MQ成功！");
	}
}
