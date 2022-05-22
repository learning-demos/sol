package com.github.dieselniu.wxshop.Test;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class LoginInAndOutTest extends IntegrationTest {
	//最开始默认状态，访问/api/status 处于未登录状态
	//发送验证码
	//带着验证码进行登录，拿到cookie
	//带着cookie放问 /api/status 应该处于登录状态
	//调用注销，注意注销登录也需要带cookie
	//再次带cookie访问，成为为登录状态


	@Test
	public void should_login_and_logout() {
		//判断登录状态
		TestResponse response = get("/api/status");
		assertThat(response.<Boolean>value("$.login")).isEqualTo(false);
		//发送验证码
		TestResponse post = post("/api/code", param());
		assertThat(post.statusCode()).isEqualTo(HttpStatus.OK);
		assertThat(post.textBody()).isEqualTo("00000");
		//登录-服务器返回cookie
		TestResponse login = post("/api/login", param1());
		List<String> header = login.header("Set-Cookie");
		String sessionId = header.stream().filter(c -> c.contains("JSESSIONID")).map(this::getSessionIdFromSetCookie).findFirst().get();
		//带着cookie去判断登录状态
		TestResponse response1 = getWithCookie("/api/status", sessionId, ImmutableMap.of());
		assertThat(response1.<Boolean>value("$.login")).isEqualTo(true);
		// 带着cookie去注销
		TestResponse testResponse = postWithCookie("/api/logout", sessionId, param3());
		//注销后判断登录状态
		TestResponse response2 = getWithCookie("/api/status", sessionId, ImmutableMap.of());
		assertThat(response2.<Boolean>value("$.login")).isEqualTo(false);

	}

	private Object param3() {
		return null;
	}

	private String getSessionIdFromSetCookie(String setCookie) {
		int index1 = setCookie.indexOf(";");
		return setCookie.substring(0, index1);
	}

	private Object param1() {
		Map<String, String> map = new HashMap<>();
		map.put("tel", "13980773401");
		map.put("code", "00000");
		return map;
	}

	private Object param() {
		Map<String, String> map = new HashMap<>();
		map.put("tel", "13980773401");
		return map;
	}
}
