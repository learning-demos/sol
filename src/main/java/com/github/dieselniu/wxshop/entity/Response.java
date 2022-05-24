package com.github.dieselniu.wxshop.entity;

public class Response<T> {
	private T data;
	private String message;

	public Response(String message, T data) {
		this.data = data;
		this.message = message;
	}

	private Response(T data) {
		this.data = data;
	}

	public static <T> Response<T> of(String message, T data) {
		return new Response<T>(message, data);
	}

	public static <T> Response<T> of(T data) {
		return new Response<T>(null, data);
	}

	public Object getData() {
		return data;
	}
}
