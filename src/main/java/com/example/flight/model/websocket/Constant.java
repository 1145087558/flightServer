package com.example.flight.model.websocket;

import com.example.flight.model.user.UserInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Constant {
    public static final String USER_TOKEN = "userId";

    public static Map<String, WebSocketServerHandshaker> webSocketHandshakerMap =
            new ConcurrentHashMap<String, WebSocketServerHandshaker>();

    /**
     * 用户对应连接的ChannelHandlerContext
     */
    public static Map<String, ChannelHandlerContext> onlineUserMap =
            new ConcurrentHashMap<String, ChannelHandlerContext>();

    /**
     * 组信息
     */
    /*public static Map<String, GroupInfo> groupInfoMap =
            new ConcurrentHashMap<String, GroupInfo>();*/

    /**
     * 用户信息
     */
    public static Map<String, UserInfo> userInfoMap =
            new HashMap<String, UserInfo>();
}
