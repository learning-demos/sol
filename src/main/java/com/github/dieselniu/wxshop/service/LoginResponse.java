package com.github.dieselniu.wxshop.service;

import com.github.dieselniu.wxshop.generate.User;
import lombok.Getter;

@Getter
public class LoginResponse {
	private boolean login;
	private User user;

	private LoginResponse(boolean login, User user) {
		this.login = login;
		this.user = user;
	}

	public static LoginResponse notLogin(boolean login, User user) {
		return new LoginResponse(false, null);
	}


	public static LoginResponse login(boolean login, User user) {
		return new LoginResponse(true, user);
	}


}
