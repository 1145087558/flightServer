package com.example.flight.service;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.flight.dao.UserDao;
import com.example.flight.entity.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;
	
	
	@Override
	public User login(User user) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_phone", user.getPhone()).eq("user_password", user.getPassword());
		user=dao.selectOne(queryWrapper);
		return user;
	}

	@Override
	public void register(User user) {
		dao.insert(user);
	}

	@Override
	public boolean sendPhoneCode(HttpServletRequest request) throws Exception{
		String phone = request.getParameter("phone");
		System.out.println(phone);
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_phone", phone);
		User user = dao.selectOne(queryWrapper);
		if(user !=null)
			return false;
		String phoneCode = smsCode();
		System.out.println(phoneCode);
		request.getSession().setAttribute("phoneCode",phoneCode);
		/*HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://utf8.api.smschinese.cn");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] data = { new NameValuePair("Uid", "a1145087558"), new NameValuePair("Key", "d41d8cd98f00b204e980"),
				new NameValuePair("smsMob", phone), new NameValuePair("smsText", "【飞机票销售平台】尊敬的用户，您好，您的验证码为：" + phoneCode + "，若非本人操作，请忽略此短信。") };
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:" + statusCode);
		for (Header h : headers) {
			System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
		System.out.println(result);

		post.releaseConnection();*/
	   
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
	
	private String smsCode() {
		String random = (int) ((Math.random() * 9 + 1) * 100000) + "";
		return random;
	}

}
