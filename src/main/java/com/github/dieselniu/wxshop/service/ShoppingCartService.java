package com.github.dieselniu.wxshop.service;

import com.github.dieselniu.wxshop.controller.AddToShoppingCartInput;
import com.github.dieselniu.wxshop.entity.ShoppingCartItem;
import com.github.dieselniu.wxshop.dao.ShoppingCartQueryMapper;
import com.github.dieselniu.wxshop.entity.DataStatus;
import com.github.dieselniu.wxshop.entity.PageResponse;
import com.github.dieselniu.wxshop.entity.ShoppingCartGoods;
import com.github.dieselniu.wxshop.entity.ShoppingCartOutput;
import com.github.dieselniu.wxshop.generate.*;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.http.HTTPException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

	private static Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);
	private final ShoppingCartQueryMapper shoppingCartQueryMapper;
	private GoodsMapper goodsMapper;
	private SqlSessionFactory sqlSessionFactory;

	@Autowired
	public ShoppingCartService(ShoppingCartQueryMapper shoppingCartQueryMapper, GoodsMapper goodsMapper, SqlSessionFactory sqlSessionFactory) {
		this.shoppingCartQueryMapper = shoppingCartQueryMapper;
		this.goodsMapper = goodsMapper;
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public PageResponse<ShoppingCartOutput> getShoppingCartOfUser(Long userId,
	                                                              int pageNumber,
	                                                              int pageSize) {

		int offset = (pageNumber - 1) * pageSize;

		int totalNum = shoppingCartQueryMapper.countHowManyShopsInUserShoppingCart(userId);


		List<ShoppingCartOutput> result = shoppingCartQueryMapper.selectShoppingCartDataByUserId(userId, pageSize, offset)
			.stream()
			.collect(Collectors.groupingBy(shoppingCartOutput -> shoppingCartOutput.getShop().getId())).values().stream().map(this::merge)
			.collect(Collectors.toList());


		int totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
		return PageResponse.pageData(pageNumber, pageSize, totalPage, result);
	}


	private ShoppingCartOutput merge(List<ShoppingCartOutput> goodsOfSameShop) {
		ShoppingCartOutput result = new ShoppingCartOutput();
		result.setShop(goodsOfSameShop.get(0).getShop());
		List<ShoppingCartGoods> goods = goodsOfSameShop.stream()
			.map(ShoppingCartOutput::getGoods)
			.flatMap(List::stream)
			.collect(Collectors.toList());
		result.setGoods(goods);
		return result;

	}


	public ShoppingCartOutput addToShoppingCart(AddToShoppingCartInput input,long userId) {
		List<Long> goodsIds = input.getGoods()
			.stream()
			.map(ShoppingCartItem::getId)
			.collect(Collectors.toList());

		if (goodsIds.isEmpty()) {
			throw new HTTPException(400);
		}


		GoodsExample example = new GoodsExample();
		example.createCriteria().andIdIn(goodsIds);
		List<Goods> goods = goodsMapper.selectByExample(example);

		if (goods.stream().map(Goods::getShopId).collect(Collectors.toSet()).size() != 1) {
			logger.debug("非法请求: {} ,{}", goodsIds, goods);
			throw new HTTPException(400);
		}


		Map<Long, Goods> idToGoodsMap = goods.stream().collect(Collectors.toMap(Goods::getId, x -> x));


		List<ShoppingCart> shoppingCarts = input.getGoods()
			.stream()
			.map(item -> toShoppingCartRow(item, idToGoodsMap))
			.collect(Collectors.toList());


		try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
			ShoppingCartMapper mapper = sqlSession.getMapper(ShoppingCartMapper.class);
			shoppingCarts.forEach(mapper::insert);
			sqlSession.commit();
		}

		return getLatestShoppingCartDataByUserIdShopId(goods.get(0).getShopId(),userId);
	}


	private ShoppingCart toShoppingCartRow(ShoppingCartItem input, Map<Long, Goods> idToGoodsMap) {
		Goods goods = idToGoodsMap.get(input.getId());
		if (goods == null) {
			return null;
		}
		ShoppingCart result = new ShoppingCart();
		result.setGoodsId(input.getId());
		result.setNumber(input.getNumber());
		result.setUserId(UserContext.getCurrentUser().getId());
		result.setShopId(goods.getShopId());
		result.setStatus(DataStatus.OK.toString().toLowerCase());
		result.setCreatedAt(new Date());
		result.setUpdatedAt(new Date());
		return result;
	}

	public ShoppingCartOutput deleteGoodsInShoppingCart(long goodsId,long userId) {
		Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
		if(goods ==null) {
			throw new HTTPException(404);
		}
		shoppingCartQueryMapper.deleteShoppingCart(goodsId,userId);

		return getLatestShoppingCartDataByUserIdShopId(goods.getShopId(),userId);

	}

	private ShoppingCartOutput getLatestShoppingCartDataByUserIdShopId(long shopId,long userId) {
		List<ShoppingCartOutput> resultRow = shoppingCartQueryMapper.selectShoppingCartDataByUserIdShopId(userId, shopId);
		return merge(resultRow);
	}
}
