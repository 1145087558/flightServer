package com.example.flight.controller.reception;

import com.example.flight.service.impl.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//@Controller
public class NettyServerContainer {

    private Thread nettyThread;
    @Autowired
    private WebSocketServer webSocketServer;


    @PostConstruct
    public void init() {
        nettyThread = new Thread(webSocketServer);
        nettyThread.start();
    }

    @PreDestroy
    public void close() {
        webSocketServer.close();
        nettyThread.stop();
        //如果isInterrupted存在资源释放的问题 可以使用nettyThread.stop();
    }
}
