package com.example.flight.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:properties/ali.properties"})
@ConfigurationProperties(prefix = "ali")
public class AlipayUtil {
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数
    public static String return_url;
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url;
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id;
    // 应用公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key;
    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key;
    // 签名方式
    public static String sign_type;
    // 字符编码格式
    public static String charset;
    // 支付宝网关
    public static String gatewayUrl;
    //支付宝公钥(和应用公钥区别是退款得用这个),建议使用这个
    public static String zifubao_public_key;


    public void setReturn_url(String return_url) {
        AlipayUtil.return_url = return_url;
    }

    public void setNotify_url(String notify_url) {
        AlipayUtil.notify_url = notify_url;
    }

    public void setApp_id(String app_id) {
        AlipayUtil.app_id = app_id;
    }

    public void setAlipay_public_key(String alipay_public_key) {
        AlipayUtil.alipay_public_key = alipay_public_key;
    }

    public void setMerchant_private_key(String merchant_private_key) {
        AlipayUtil.merchant_private_key = merchant_private_key;
    }

    public void setSign_type(String sign_type) {
        AlipayUtil.sign_type = sign_type;
    }

    public void setCharset(String charset) {
        AlipayUtil.charset = charset;
    }

    public void setGatewayUrl(String gatewayUrl) {
        AlipayUtil.gatewayUrl = gatewayUrl;
    }

    public void setZifubao_public_key(String zifubao_public_key) {
        AlipayUtil.zifubao_public_key = zifubao_public_key;
    }
}