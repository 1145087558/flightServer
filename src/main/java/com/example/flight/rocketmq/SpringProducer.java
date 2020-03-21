package com.example.flight.rocketmq;


import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class SpringProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;//在RocketMQAutoConfiguration实例化


    public void sendMsg(String topic, Object msg) {
        System.out.println(msg);
        this.rocketMQTemplate.convertAndSend(topic, msg);
    }

    public void sync(String topic, String msg) {
        rocketMQTemplate.syncSend(topic,msg);
    }

    public void async(String topic, String msg) {
        System.out.println("producer:"+msg);
        rocketMQTemplate.asyncSend(topic,msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("mq成功发送");
            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
    }

    //用于日志信息发送
    //跟异步发送的区别就是它没有回调函数来告知
    public void oneWay(String topic, String msg) {
        rocketMQTemplate.sendOneWay(topic,msg);
    }

}
