package com.github.dieselniu.wxshop.dao;

import com.github.dieselniu.wxshop.entity.DataStatus;
import com.github.dieselniu.wxshop.entity.Response;
import com.github.dieselniu.wxshop.exception.ResourceNotFoundException;
import com.github.dieselniu.wxshop.generate.Goods;
import com.github.dieselniu.wxshop.generate.GoodsMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GoodsDao {
	private final GoodsMapper mapper;

	public GoodsDao(GoodsMapper mapper) {
		this.mapper = mapper;
	}

	public Goods insertGood(Goods goods) {
		long id = mapper.insert(goods);
		goods.setId(id);
		return goods;
	}

	public Goods deleteGoodById(Long id) {
		Goods goods = mapper.selectByPrimaryKey(id);
		if (Objects.isNull(goods)) throw new ResourceNotFoundException("商品为找到");
		goods.setStatus(DataStatus.DELETE_STATUS);
		mapper.updateByPrimaryKey(goods);
		return goods;
	}
}
