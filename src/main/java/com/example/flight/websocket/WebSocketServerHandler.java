package com.example.flight.websocket;

import com.alibaba.fastjson.JSONObject;
import com.example.flight.model.websocket.ChatMessage;
import com.example.flight.model.websocket.Constant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;
import io.netty.channel.ChannelHandler.Sharable;

import java.util.Iterator;
import java.util.Map.Entry;

//处理WebSocket请求
@Component("webSocketServerHandler")
@Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //接收数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {

        String content = frame.text();
        ChatMessage chatMessage = JSONObject.parseObject(content,ChatMessage.class);
        String type = chatMessage.getType();
        switch (type){
            case "REGISTER":register(chatMessage,ctx);
            break;
            case "SEND":sendMessage(chatMessage);
            break;
            default:
                break;
        }
    }

    //断开连接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Iterator<Entry<String, ChannelHandlerContext>> iterator =
                Constant.onlineUserMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Entry<String, ChannelHandlerContext> entry = iterator.next();
            if (entry.getValue() == ctx) {
                //Constant.webSocketHandshakerMap.remove(ctx.channel().id().asLongText());
                iterator.remove();
                break;
            }
        }
    }

    //异常处理：关闭channel
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private  void register(ChatMessage chatMessage,ChannelHandlerContext ctx){
             Constant.onlineUserMap.put(chatMessage.getFromId().toString(),ctx);
    }

    private  void sendMessage(ChatMessage chatMessage){
        ChannelHandlerContext toUserCtx = Constant.onlineUserMap.get(chatMessage.getToId());
        String msg = JSONObject.toJSONString(chatMessage);
        toUserCtx.channel().writeAndFlush(new TextWebSocketFrame(msg));
    }
}
