package com.example.flight.util;


public class PhoneUtil {

	// 短信应用SDK AppID
	public static int appId=1400182100;
	// 短信应用SDK AppKey
	public static String appkey="fb4d6e83f14c383a8daabb65ea31328f";
	// 短信模板ID
	public static int templateId = 555551;
	// 签名内容
	public static String smsSign = "个人springboot";

	public static String smsCode() {
		String random = (int) ((Math.random() * 9 + 1) * 100000) + "";
		return random;
	}
}
