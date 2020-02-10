package com.example.flight.service;

import javax.servlet.http.HttpServletRequest;

import com.example.flight.entity.User;

public interface UserService {

	public User login(User user);
	public void register(User user);
	public boolean sendPhoneCode(HttpServletRequest request) throws Exception;
	public boolean checkCode(HttpServletRequest request);
}
