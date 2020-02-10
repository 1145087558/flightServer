package com.example.flight.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.flight.converter.DateConverter;
import com.example.flight.interceptor.LoginInterceptor;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

	@Autowired
	private LoginInterceptor loginInterceptor;

	@Autowired
	private DateConverter dateConverter;


	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		WebMvcConfigurer.super.addCorsMappings(registry);
	}
	
	//这个方法是用来配置静态资源的
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
	}

	// 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// addPathPatterns("/**") 表示拦截所有的请求，
		// excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
		
		registry.addInterceptor(loginInterceptor).addPathPatterns("/subscribeInfo","/placeorder","/orderlist",
				"/zifubaoPay");
	}

	//用来注册日期转换器
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(dateConverter);
	}
	
	
}
