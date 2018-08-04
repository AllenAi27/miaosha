package com.imooc.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
	
	@Autowired
	private JedisPool jedisPool;
	
	public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(key == null || key.length() <= 0) {
				return null;
			}
			//生成真正的key
			String realKey = prefix.getPrefix() + key;
			String str = jedis.get(realKey);
			T t = stringToBean(str,clazz);
			return t;
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	public <T> boolean set(KeyPrefix prefix, String key, T t) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String value = beanToString(t);
			if(value == null || value.length() <= 0) {
				return false;
			}
			//生成真正的key
			String realKey = prefix.getPrefix() + key;
			int expireSeconds = prefix.expireSeconds();
			if(expireSeconds <= 0) {
				jedis.set(realKey, value);
			}else {
				jedis.setex(realKey, expireSeconds, value);
			}
			return true;
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Boolean exist(KeyPrefix prefix, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(key == null || key.length() <= 0) {
				return false;
			}
			//生成真正的key
			String realKey = prefix.getPrefix() + key;
			return jedis.exists(realKey);
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Long incr(KeyPrefix prefix, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(key == null || key.length() <= 0) {
				return null;
			}
			//生成真正的key
			String realKey = prefix.getPrefix() + key;
			return jedis.incr(realKey);
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Long decr(KeyPrefix prefix, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(key == null || key.length() <= 0) {
				return null;
			}
			//生成真正的key
			String realKey = prefix.getPrefix() + key;
			return jedis.decr(realKey);
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Boolean delete(KeyPrefix prefix, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(key == null || key.length() <= 0) {
				return false;
			}
			//生成真正的key
			String realKey = prefix.getPrefix() + key;
			Long result = jedis.del(realKey);
			return result > 0;
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	private <T> String beanToString(T t) {
		if(t == null) {
			return null;
		}
		if(t.getClass() == int.class || t.getClass() == Integer.class) {
			return "" + t;
		}else if(t.getClass() == String.class) {
			return (String) t;
		}else if(t.getClass() == long.class || t.getClass() == Long.class) {
			return "" + t;
		}else {
			return JSON.toJSONString(t);
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T stringToBean(String str, Class<T> clazz) {
		if(str == null || str.length() <= 0 || clazz == null) {
			return null;
		}
		if(clazz == int.class || clazz == Integer.class) {
			return (T) Integer.valueOf(str);
		}else if(clazz == String.class) {
			return (T) str;
		}else if(clazz == long.class || clazz == Long.class) {
			return (T) Long.valueOf(str);
		}else {
			return JSON.toJavaObject(JSON.parseObject(str), clazz);
		}
	}

	
}
