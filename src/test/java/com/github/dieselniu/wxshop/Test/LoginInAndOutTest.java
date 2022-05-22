package com.github.dieselniu.wxshop.Test;

import org.junit.jupiter.api.Test;

public class LoginInAndOutTest extends IntegrationTest {
	//最开始默认状态，访问/api/status 处于未登录状态
	//发送验证码
	//带着验证码进行登录，拿到cookie
	//带着cookie放问 /api/status 应该处于登录状态
	//调用注销，注意注销登录也需要带cookie
	//再次带cookie访问，成为为登录状态
	@Test
	public void should_(){

	}
}
