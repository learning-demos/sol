package com.github.dieselniu.wxshop.service;

import com.github.dieselniu.wxshop.entity.DataStatus;
import com.github.dieselniu.wxshop.entity.PageResponse;
import com.github.dieselniu.wxshop.exception.NotAuthorizationForShopException;
import com.github.dieselniu.wxshop.exception.ResourceNotFoundException;
import com.github.dieselniu.wxshop.generate.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GoodService {
	private final GoodsMapper goodsMapper;
	private final ShopMapper shopMapper;

	public GoodService(GoodsMapper goodsMapper, ShopMapper shopMapper) {
		this.goodsMapper = goodsMapper;
		this.shopMapper = shopMapper;
	}

	public Goods createGoods(Goods goods) {
		Shop shop = shopMapper.selectByPrimaryKey(goods.getShopId());
		if (Objects.deepEquals(shop.getOwnerUserId(), UserContext.getCurrentUser().getId())) {
			long id = goodsMapper.insert(goods);
			goods.setId(id);
			return goods;
		} else {
			throw new NotAuthorizationForShopException("无权访问");
		}
	}

	public Goods deleteById(Long id) {
		Shop shop = shopMapper.selectByPrimaryKey(id);
		if (Objects.deepEquals(shop.getOwnerUserId(), UserContext.getCurrentUser().getId())) {
			Goods goods = goodsMapper.selectByPrimaryKey(id);
			if (goods == null) {
				throw new ResourceNotFoundException("商品未找到");
			}
			goods.setStatus(DataStatus.DELETED.getName());
			goodsMapper.updateByPrimaryKey(goods);
			return goods;
		} else {
			throw new NotAuthorizationForShopException("无权访问");
		}
	}

	public PageResponse<Goods> getGoods(Integer pageNum, Integer pageSize, Integer shopId) {
		int totalNumber = countGoods(shopId);
		int totalPage = totalNumber % pageSize == 0 ? totalNumber / pageSize : totalNumber / pageSize + 1;
		GoodsExample page = new GoodsExample();
		page.setLimit(pageSize);
		page.setOffset((pageNum - 1) * pageSize);
		List<Goods> goods = goodsMapper.selectByExample(page);
		return PageResponse.pageData(pageNum, pageSize, totalPage, goods);
	}

	private int countGoods(Integer shopId) {
		if (shopId == null) {
			GoodsExample example = new GoodsExample();
			example.createCriteria().andStatusEqualTo(DataStatus.DELETED.getName());
			return (int) goodsMapper.countByExample(example);
		} else {
			GoodsExample example = new GoodsExample();
			example.createCriteria().andStatusEqualTo(DataStatus.DELETED.getName())
				.andShopIdEqualTo(shopId.longValue());
			return (int) goodsMapper.countByExample(example);

		}
	}

	public Goods updateGoods(Goods goods) {
		Shop shop = shopMapper.selectByPrimaryKey(goods.getShopId());
		if (shop == null || Objects.deepEquals(shop.getOwnerUserId(), UserContext.getCurrentUser().getId())) {
			GoodsExample byId = new GoodsExample();
			byId.createCriteria().andIdEqualTo(goods.getId());
			int affectedRow = goodsMapper.updateByExample(goods, byId);
			if (affectedRow == 0) {
				throw new ResourceNotFoundException("未找到");
			}
			return goods;
		} else {
			throw new NotAuthorizationForShopException("无权访问");
		}
	}
}
