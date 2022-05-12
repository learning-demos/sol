package com.github.dieselniu.wxshop;

import com.github.dieselniu.wxshop.service.AuthService;
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

	@GetMapping("/niu")
	public String niu() {
		return "ok";
	}

	@PostMapping("/code")
	public void code(@RequestBody TelAndCode telAndCode) {
		authService.sendVerificationCode(telAndCode.getTel());

	}

	@PostMapping("/login")
	public void login(@RequestBody TelAndCode telAndCode) {
		UsernamePasswordToken token = new UsernamePasswordToken(telAndCode.getTel(), telAndCode.getCode());
		token.setRememberMe(true);
		SecurityUtils.getSubject().login(token);
	}

	public static class TelAndCode {
		private String code;
		private String tel;

		public TelAndCode(String code, String tel) {
			this.code = code;
			this.tel = tel;
		}

		public String getCode() {
			return code;
		}

		public String getTel() {
			return tel;
		}
	}
}
