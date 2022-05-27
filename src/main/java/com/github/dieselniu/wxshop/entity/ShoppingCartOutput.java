package com.github.dieselniu.wxshop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dieselniu.wxshop.generate.Shop;

import java.util.List;

public class ShoppingCartOutput {
	private @JsonProperty("shop") Shop shop;
	private @JsonProperty("goods") List<ShoppingCartGoods> goods;

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<ShoppingCartGoods> getGoods() {
		return goods;
	}

	public void setGoods(List<ShoppingCartGoods> goods) {
		this.goods = goods;
	}
}
