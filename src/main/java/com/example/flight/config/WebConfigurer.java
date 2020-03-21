package com.example.flight.config;

import com.example.flight.listener.RequestListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
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
	@Autowired
	private RequestListener requestListener;


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
		// excludePathPatterns("/login", "/register") 表示除了登陆与注册之外
		
		registry.addInterceptor(loginInterceptor).addPathPatterns("/**").
				excludePathPatterns("/serachByWeek.action/*","/getFlighInfo.action","/getDateWeek.action",
				"/serachList.action","/login.action","/sendPhoneCode.action","/checkCode.action","/register.action",
						"/manageLogin.form");
	}

	//用来注册日期转换器
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(dateConverter);
	}

	//注册监听器
	//也可以@WebListener注解来实现，启动类添加@ServletComponentScan注解
	@Bean
	public ServletListenerRegistrationBean<RequestListener> servletListenerRegistrationBean() {
		ServletListenerRegistrationBean<RequestListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<>();
		servletListenerRegistrationBean.setListener(requestListener);
		return servletListenerRegistrationBean;
	}


}
