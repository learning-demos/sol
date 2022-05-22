package com.github.dieselniu.wxshop;

import com.github.dieselniu.wxshop.service.AuthService;
import com.github.dieselniu.wxshop.service.LoginResponse;
import com.github.dieselniu.wxshop.service.UserContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/code")
	public String code(@RequestBody TelAndCode telAndCode) {
		return authService.sendVerificationCode(telAndCode.getTel());

	}


	@GetMapping("/status")
	public Object loginStatus() {
		if (UserContext.getCurrentUser() == null) {
			return LoginResponse.notLogin(false, null);
		} else {
			return LoginResponse.login(true, UserContext.getCurrentUser());
		}
	}


	@GetMapping("/niu")
	public Object login() {
		return "ok";
	}

	@PostMapping("/login")
	public void login(@RequestBody TelAndCode telAndCode) {
		UsernamePasswordToken token = new UsernamePasswordToken(telAndCode.getTel(), telAndCode.getCode());
		token.setRememberMe(true);
		SecurityUtils.getSubject().login(token);
	}

	@PostMapping("/logout")
	public void logout() {
		SecurityUtils.getSubject().logout();
	}
}
