package com.example.flight;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.flight.entity.User;
import com.example.flight.service.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightApplicationTests {

	@Autowired
	UserService service;
	
	@Test
	public void login() {
		User user = new User().setUserName("123456").setPassword("12345");
		System.out.println(service.login(user));
	}
	
	
	

}
