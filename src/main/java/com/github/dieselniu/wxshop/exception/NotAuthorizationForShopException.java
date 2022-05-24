package com.github.dieselniu.wxshop.exception;

import jdk.internal.dynalink.support.RuntimeContextLinkRequestImpl;

public class NotAuthorizationForShopException extends  RuntimeException {
	private String message;

	public NotAuthorizationForShopException(String message) {
		this.message = message;
	}
}
