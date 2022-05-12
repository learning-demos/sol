package com.github.dieselniu.wxshop.service;

public interface SmsCodeService {
	/**
	 * 向一个手机号发验证码，返回正确答案
	 * @param tel
	 * @return 正确答案
	 */
	String sendSmsCode(String tel);
}
