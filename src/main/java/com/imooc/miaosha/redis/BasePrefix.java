package com.imooc.miaosha.redis;

/**
 * abstract类  定义公共部分
 * @author aiqilong
 *
 */
public abstract class BasePrefix implements KeyPrefix {
	
	private int expireSeconds;
	
	private String prefix;
	
	public BasePrefix(String prefix) {
		//定义0为用不过期
		this(0, prefix);
	}
	
	public BasePrefix(int expireSeconds, String prefix) {
		this.expireSeconds = expireSeconds;
		this.prefix = prefix;
	}

	@Override
	public int expireSeconds() {
		return expireSeconds;
	}

	@Override
	public String getPrefix() {
		String className = getClass().getSimpleName();
		return className + ":" + prefix;
	}
	
}
