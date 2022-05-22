package com.github.dieselniu.wxshop.config;

import com.github.dieselniu.wxshop.service.ShiroRealm;
import com.github.dieselniu.wxshop.service.UserService;
import com.github.dieselniu.wxshop.service.VerificationCodeCheckService;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig implements WebMvcConfigurer {

	@Autowired
	UserService userService;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserLoginInterceptor(userService));
	}


	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		Map<String, String> pattern = new HashMap<>();
		//排除掉注册登录接口
		pattern.put("/api/code", "anon");
		pattern.put("/api/login", "anon");


		shiroFilterFactoryBean.setFilterChainDefinitionMap(pattern);
		return shiroFilterFactoryBean;
	}

	@Bean
	public SecurityManager securityManager(ShiroRealm shiroRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm);
		securityManager.setCacheManager(new MemoryConstrainedCacheManager());
		securityManager.setSessionManager(new DefaultWebSessionManager());
		return securityManager;
	}


	@Bean
	public ShiroRealm myShiroRealm(VerificationCodeCheckService verificationCodeCheckService) {
		return new ShiroRealm(verificationCodeCheckService);
	}


}
