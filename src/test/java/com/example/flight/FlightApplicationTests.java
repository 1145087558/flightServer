package com.example.flight;

import com.example.flight.dao.UserDao;
import com.example.flight.rocketmq.SpringProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.flight.model.user.UserInfo;
import com.example.flight.service.UserService;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightApplicationTests {

	@Autowired
	UserService service;

	@Autowired
	UserDao dao;

	@Autowired
	StringRedisTemplate redisTemplate;

	@Autowired
	private SpringProducer springProducer;
	
	@Test
	public void login() {
		UserInfo user = new UserInfo().setUserName("123456").setPassword("12345");
		System.out.println(service.login(user));
	}

	@Test
	public void redisTest() {
		redisTemplate.opsForValue().set("hello", "world");
		String value = redisTemplate.opsForValue().get("hello");
		redisTemplate.delete("hello");
		value = redisTemplate.opsForValue().get("hello");
		System.out.println(value);
	}

	@Test
	public void testSendMessage() {
		springProducer.sendMsg("my-topic","123456");
	}


	@Test
	@Transactional
	public void transactionTest() {
		UserInfo user = new UserInfo().setUserName("123456").setPassword("12345");
		dao.insert(user);
		int i=10/0;
		user.setId(1);
		dao.updateById(user);
	}

}
