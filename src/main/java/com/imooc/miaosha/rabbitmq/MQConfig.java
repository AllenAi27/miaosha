package com.imooc.miaosha.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MQConfig {
	
	public static final String QUEUE = "queue";
	public static final String TOPIC_QUEUE_1 = "topic.queue1";
	public static final String TOPIC_QUEUE_2 = "topic.queue2";
	public static final String TOPIC_EXCHANGE = "topicExchange";
	public static final String FANOUT_EXCHANGE = "fanoutExchange";
	public static final String HEADERS_EXCHANGE = "headersExchange";
	public static final String HEADERS_QUEUE = "headers.queue";

	/**
	 * Direct模式 Exchange交换机
	 * @param message
	 */
	@Bean
	public Queue queue() {
		return new Queue(QUEUE, true);
	}
	
	/**
	 * Topic模式 Exchange交换机
	 * @param message
	 */
	@Bean
	public Queue topicQueue1() {
		return new Queue(TOPIC_QUEUE_1, true);
	}
	
	/**
	 * Topic模式 Exchange交换机
	 * @param message
	 */
	@Bean
	public Queue topicQueue2() {
		return new Queue(TOPIC_QUEUE_2, true);
	}
	
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(TOPIC_EXCHANGE);
	}
	
	@Bean
	public Binding topicBinding1() {
		return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
	}
	
	@Bean
	public Binding topicBinding2() {
		return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
	}
	
	/**
	 * Fanout模式 Exchange交换机------广播模式
	 * @param message
	 */
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(FANOUT_EXCHANGE);
	}
	
	@Bean
	public Binding FanoutBinding1() {
		return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
	}
	
	@Bean
	public Binding FanoutBinding2() {
		return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
	}
	
	/**
	 * Headers模式 Exchange交换机
	 * @param message
	 */
	@Bean
	public HeadersExchange headersExchange() {
		return new HeadersExchange(HEADERS_EXCHANGE);
	}
	
	@Bean
	public Queue headersQueue() {
		return new Queue(HEADERS_QUEUE, true);
	}
	
	@Bean
	public Binding headersBinding() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("header1", "value1");
		map.put("header2", "value2");
		return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(map).match();
	}
}
