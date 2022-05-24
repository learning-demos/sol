package com.github.dieselniu.wxshop.exception;

public class ResourceNotFoundException extends RuntimeException {
	private final String message;

	public ResourceNotFoundException(String message) {
		this.message = message;
	}
}
