package com.imooc.miaosha.redis;

/**
 * 接口定义规范
 * @author aiqilong
 *
 */
public interface KeyPrefix {
	
	public int expireSeconds();
	
	public String getPrefix();
}
