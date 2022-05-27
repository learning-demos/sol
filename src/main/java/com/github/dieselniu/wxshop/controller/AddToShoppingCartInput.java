package com.github.dieselniu.wxshop.controller;

import com.github.dieselniu.wxshop.entity.ShoppingCartItem;
import lombok.Getter;

import java.util.List;

@Getter
public class AddToShoppingCartInput {
	private List<ShoppingCartItem> goods;


	public List<ShoppingCartItem> getGoods() {
		return goods;
	}

	public void setGoods(List<ShoppingCartItem> goods) {
		this.goods = goods;
	}
}
