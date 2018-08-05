package com.imooc.miaosha.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MQRecevier {
	
	private static Logger log = LoggerFactory.getLogger(MQRecevier.class);
	
	/**
	 * Direct模式 Exchange交换机
	 * @param message
	 */
	@RabbitListener(queues = MQConfig.QUEUE)
	public void receive(String message) {
		log.info("receive message: " + message);
	}

	/**
	 * Topic模式 Exchange交换机
	 * @param message
	 */
	@RabbitListener(queues = MQConfig.TOPIC_QUEUE_1)
	public void receiveTopic1(String message) {
		log.info("receive topic queue1 message: " + message);
	}
	
	/**
	 * Topic模式 Exchange交换机
	 * @param message
	 */
	@RabbitListener(queues = MQConfig.TOPIC_QUEUE_2)
	public void receiveTopic2(String message) {
		log.info("receive topic queue2 message: " + message);
	}
	
	/**
	 * Headers模式 Exchange交换机
	 * @param message
	 */
	@RabbitListener(queues = MQConfig.HEADERS_QUEUE)
	public void receiveHeaders(byte[] message) {
		log.info("receive headers queue message: " + new String(message));
	}
}
