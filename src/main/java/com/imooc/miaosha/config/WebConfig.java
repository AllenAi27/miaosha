package com.imooc.miaosha.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 当传到服务端的参数中有MiaoshaUser时，
 * 会执行resolveArgument将值封装到MiaoshaUser中
 * @author aiqilong
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
	
	@Autowired
	private UserArgumentResolver userArgResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userArgResolver);
	}
	
	

}
