package com.github.dieselniu.wxshop.service;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

@Service
public class ShiroRealm extends AuthorizingRealm {

	private final VerificationCodeCheckService verificationCodeCheckService;

	public ShiroRealm(VerificationCodeCheckService verificationCodeCheckService) {
		this.verificationCodeCheckService = verificationCodeCheckService;
		this.setCredentialsMatcher((authenticationToken, authenticationInfo) ->
			new java.lang.String((char[]) authenticationToken.getCredentials()).equals(authenticationInfo.getCredentials()));
	}

	@Override //你没有没有权限访问这个资源，你的门禁卡有没有进去这道门的权利
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		return null;
	}

	@Override //看看你的身份证和你的照片对不对的上，看看这个人是不是你说的自己
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		String tel = (String) authenticationToken.getPrincipal();
		String correctCode = verificationCodeCheckService.getCorrectCode(tel);
		return new SimpleAuthenticationInfo(tel, correctCode, getName());
	}
}
