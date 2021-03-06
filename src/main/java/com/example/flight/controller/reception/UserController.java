package com.example.flight.controller.reception;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.flight.model.user.UserInfo;
import com.example.flight.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService service;
	
	@PostMapping("login.action")
	public void login(UserInfo user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		user = service.login(user);
		response.setCharacterEncoding("UTF-8");
		if(user!=null) {
			request.getSession().setAttribute("user", user);
			response.getWriter().write("成功");

			Cookie cookie = new Cookie("user",user.getUserName());
			cookie.setPath("/");
			response.addCookie(cookie);
		}else {
			response.getWriter().write("失败");
		}
	}
	
	@PostMapping("register.action")
	public void register(HttpServletRequest request, HttpServletResponse response, UserInfo user) {
		service.register(user);
		System.out.println(user);
		request.getSession().setAttribute("user", user);
		Cookie cookie = new Cookie("user",user.getUserName());
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	@RequestMapping("sendPhoneCode.action")
	public void sendPhoneCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
		if(service.sendPhoneCode(request)) {
			response.getWriter().write("发送成功");
		}else {
			response.getWriter().write("手机号已被注册");
		}
	}
	
	@RequestMapping("checkCode.action")
	public void checkCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		if(service.checkCode(request)) {
			response.getWriter().write("验证成功");
		}else {
			response.getWriter().write("验证码错误");
		}
	}
	
	@RequestMapping("logout.action")
	public void logout(HttpServletRequest request,HttpServletResponse response){
		request.getSession().removeAttribute("user");
		request.getSession().invalidate();
		Cookie cookie = new Cookie("user","");
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	@GetMapping("getSessionUser")
	public UserInfo getSessionUser(HttpServletRequest request){
		UserInfo user = (UserInfo)request.getSession().getAttribute("user");
		return user;
	}
	
	@RequestMapping("getCookie")
	public String getCookie(HttpServletRequest request,HttpServletResponse response){
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null && cookies.length >0) {
	        for (Cookie cookie : cookies) {
	            System.out.println("name:" + cookie.getName() + "-----value:" + cookie.getValue());
	        }
	    }
	    return "success";
	}
	
	
}
