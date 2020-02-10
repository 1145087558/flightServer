package com.example.flight.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.OnMessage;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;

@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketService {

	public static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

	@OnOpen
	public void onOpen(@PathParam("userId") String userId, Session session) {
		if (sessionMap == null) {
			sessionMap = new ConcurrentHashMap<String, Session>();
		}
		sessionMap.put(userId, session);
	}

	@OnClose
	public void OnClose(@PathParam("userId") String userId) {
		sessionMap.remove(userId);
	}

	@OnMessage
	public void OnMessage(@PathParam("userId") String userId, Session session, String message) {
		System.out.println(message);
		for (Session session_ : sessionMap.values()) {
			session_.getAsyncRemote().sendText(message);//异步发送消息
			//session_.getBasicRemote().sendText(message);同步发送消息
		}
	}
	
	@OnError
	public void error(Session session, Throwable t) {
		t.printStackTrace();
	}

}
