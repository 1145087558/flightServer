package com.example.flight.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "order-topic", consumerGroup = "order-group", selectorExpression = "*")
public class OrderConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        System.out.println("订单消费:"+msg);
    }
}
