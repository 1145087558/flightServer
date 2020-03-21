package com.example.flight.service;

import javax.servlet.http.HttpServletRequest;

import com.example.flight.model.user.UserInfo;

import java.util.List;

public interface UserService {

	UserInfo login(UserInfo user);
	UserInfo manageLogin(UserInfo user);
	void register(UserInfo user);
	boolean sendPhoneCode(HttpServletRequest request) throws Exception;
	boolean checkCode(HttpServletRequest request);
	List<UserInfo> getUserAllList();
	void modifyStatus(int userId,int status);
	void modifyUser(UserInfo user);
	UserInfo getUserById(int userId);
	void deleteUser(Integer userId);
	List<UserInfo> getUserDeleteList();
}
