package com.github.dieselniu.wxshop.dao;

import com.github.dieselniu.wxshop.generate.Shop;
import com.github.dieselniu.wxshop.generate.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopDao {
	private final ShopMapper shopMapper;

	@Autowired
	public ShopDao(ShopMapper shopMapper) {
		this.shopMapper = shopMapper;
	}


	public Shop findShopById(Long shopId) {
		return shopMapper.selectByPrimaryKey(shopId);
	}
}
