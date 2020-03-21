package com.example.flight.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.example.flight.model.user.PhoneInfo;
import com.example.flight.util.PhoneUtil;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RocketMQMessageListener(topic = "register-topic", consumerGroup = "register-group", selectorExpression = "*")
public class RegisterConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {

        PhoneInfo phoneInfo = JSONObject.parseObject(msg, PhoneInfo.class);
        System.out.println("消费:"+phoneInfo);

        //数组具体的元素个数和模板中变量个数必须一致
        String[] params = {phoneInfo.getPhoneCode()};
        SmsSingleSender ssender = new SmsSingleSender(PhoneUtil.appId, PhoneUtil.appkey);
        SmsSingleSenderResult result = null; // 签名参数未提供或者为空时，会使用默认签名发送短信
        try {
            result = ssender.sendWithParam("86", phoneInfo.getPhone(), PhoneUtil.templateId,
                    params, PhoneUtil.smsSign, "", "");
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(result.toString());
    }
}
