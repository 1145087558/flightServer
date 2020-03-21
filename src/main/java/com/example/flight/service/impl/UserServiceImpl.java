package com.example.flight.service.impl;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.example.flight.model.user.PhoneInfo;
import com.example.flight.rocketmq.SpringProducer;
import com.example.flight.service.UserService;
import com.example.flight.util.PhoneUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.flight.dao.UserDao;
import com.example.flight.model.user.UserInfo;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao dao;
	@Autowired
	private SpringProducer producer;
	
	
	@Override
	//@Cacheable(cacheNames="user_cache")
	public UserInfo login(UserInfo user) {
		QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_phone", user.getPhone()).eq("user_password", user.getPassword())
				.and(wrapper ->wrapper.eq("user_status",0).or().eq("user_status",1));
		/*queryWrapper.eq("user_phone", user.getPhone()).eq("user_password", user.getPassword())
				.apply("( user_status = 0 or user_status = 1 )");*/
		user=dao.selectOne(queryWrapper);
		return user;
	}

	@Override
	public UserInfo manageLogin(UserInfo user) {
		QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_name", user.getUserName()).eq("user_password", user.getPassword())
		.eq("user_status",2);
		user=dao.selectOne(queryWrapper);
		return user;
	}

	@Override
	//@CachePut(cacheNames="user_cache")
	public void register(UserInfo user) {
		dao.insert(user);
	}

	@Override
	public boolean sendPhoneCode(HttpServletRequest request){
		String phone = request.getParameter("phone");
		System.out.println(phone);
		QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_phone", phone);
		UserInfo user = dao.selectOne(queryWrapper);
		if(user !=null)
			return false;
		String phoneCode = PhoneUtil.smsCode();
		System.out.println(phoneCode);
		request.getSession().setAttribute("phoneCode",phoneCode);

		PhoneInfo phoneInfo = new PhoneInfo(phone,phoneCode);
		producer.async("register-topic", JSON.toJSONString(phoneInfo));

		return true;
	}
	
	@Override
	public boolean checkCode(HttpServletRequest request) {
		String code = request.getParameter("code");
		String phoneCode = (String)request.getSession().getAttribute("phoneCode");
		if(code.equals(phoneCode)) {
			return true;
		}
		return false;
	}

	@Override
	//@Cacheable(cacheNames="user_cache")
	public List<UserInfo> getUserAllList() {
		QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_status",0).or().eq("user_status",1)
		.or().eq("user_status",3);
		return dao.selectList(queryWrapper);
	}

	@Override
	public void modifyStatus(int userId, int status) {
		UserInfo userInfo = dao.selectById(userId);
		userInfo.setStatus(status);
		dao.updateById(userInfo);
	}

	@Override
	public void modifyUser(UserInfo user) {
		dao.updateById(user);
	}

	@Override
	public UserInfo getUserById(int userId) {
		return dao.selectById(userId);
	}

	@Override
	public void deleteUser(Integer userId) {
		dao.deleteById(userId);
	}

	@Override
	public List<UserInfo> getUserDeleteList() {
		QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_status",4);
		return dao.selectList(queryWrapper);
	}

}
