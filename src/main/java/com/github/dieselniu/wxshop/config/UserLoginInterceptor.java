package com.github.dieselniu.wxshop.config;

import com.github.dieselniu.wxshop.generate.User;
import com.github.dieselniu.wxshop.service.UserContext;
import com.github.dieselniu.wxshop.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginInterceptor implements HandlerInterceptor {

	private UserService userService;

	public UserLoginInterceptor(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		Object tel = SecurityUtils.getSubject().getPrincipal();
		if (tel != null) {
			User user = userService.getUserByTel(tel.toString());
			UserContext.setCurrentUser(user);
		}
		System.out.println("Pre!");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		UserContext.setCurrentUser(null);
	}
}
