package com.github.dieselniu.wxshop.entity;

import lombok.Getter;

@Getter
public class ShoppingCartItem {
	private long id;
	private int number;

	public ShoppingCartItem(long id, int number) {
		this.id = id;
		this.number = number;
	}
}
