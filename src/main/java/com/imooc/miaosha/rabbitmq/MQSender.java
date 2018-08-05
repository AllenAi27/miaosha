package com.imooc.miaosha.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.miaosha.redis.RedisService;

@Service
public class MQSender {
	
	private static Logger log = LoggerFactory.getLogger(MQSender.class);
	
	@Autowired
	private AmqpTemplate template;
	
	public void send(Object obj) {
		String message = RedisService.beanToString(obj);
		log.info("send message: " + message);
		template.convertAndSend(MQConfig.QUEUE, message);
	}
	
	public void sendTopic(Object obj) {
		String msg = RedisService.beanToString(obj);
		log.info("send topic queue message: " + msg);
		template.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg + "1");
		template.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg + "2");
	}
	
	public void sendFanout(Object obj) {
		String msg = RedisService.beanToString(obj);
		log.info("send fanout queue message: " + msg);
		template.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg);
	}
	
	public void sendHeaders(Object obj) {
		String msg = RedisService.beanToString(obj);
		log.info("send headers queue message: " + msg);
		MessageProperties prop = new MessageProperties();
		prop.setHeader("header1", "value1");
		prop.setHeader("header2", "value2");
		Message message = new Message(msg.getBytes(), prop);
		template.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", message);
	}
}
