package com.github.dieselniu.wxshop.service;

import com.github.dieselniu.wxshop.dao.GoodsDao;
import com.github.dieselniu.wxshop.dao.ShopDao;
import com.github.dieselniu.wxshop.entity.Response;
import com.github.dieselniu.wxshop.exception.NotAuthorizationForShopException;
import com.github.dieselniu.wxshop.generate.Goods;
import com.github.dieselniu.wxshop.generate.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GoodService {
	private final GoodsDao goodsDao;
	private final ShopDao shopDao;

	@Autowired
	public GoodService(GoodsDao goodsDao, ShopDao shopDao) {
		this.goodsDao = goodsDao;
		this.shopDao = shopDao;
	}

	public Goods createGoods(Goods goods) {
		Shop shop = shopDao.findShopById(goods.getShopId());
		if (Objects.deepEquals(shop.getOwnerUserId(), UserContext.getCurrentUser().getId())) {
			return goodsDao.insertGood(goods);
		} else {
			throw new NotAuthorizationForShopException("无权访问");
		}
	}

	public Goods deleteById(Long id) {
		Shop shop = shopDao.findShopById(id);
		if (Objects.deepEquals(shop.getOwnerUserId(), UserContext.getCurrentUser().getId())) {
			return goodsDao.deleteGoodById(id);
		} else {
			throw new NotAuthorizationForShopException("无权访问");
		}
	}
}
