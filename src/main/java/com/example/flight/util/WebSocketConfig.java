package com.example.flight.util;

import javax.websocket.server.ServerEndpointConfig.Configurator;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig extends Configurator implements ApplicationContextAware{

	private static volatile BeanFactory context;

	//内置tomcat需要自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
	@Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
	
	//@Autowired不能注入问题
	@Override
	public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
		return context.getBean(clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		WebSocketConfig.context = applicationContext;
	}

}
