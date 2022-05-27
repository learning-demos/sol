package com.github.dieselniu.wxshop.dao;

import com.github.dieselniu.wxshop.entity.ShoppingCartOutput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShoppingCartQueryMapper {

	int countHowManyShopsInUserShoppingCart(long userId);

	List<ShoppingCartOutput> selectShoppingCartDataByUserId(
		@Param("userId") long userId,
		@Param("limit") int limit,
		@Param("offset") int offset
	);


	List<ShoppingCartOutput> selectShoppingCartDataByUserIdShopId(
		@Param("userId") long userId,
		@Param("shopId") long shopId
	);


	void deleteShoppingCart(@Param("goodsId") long goodsId,
	                        @Param("userId") long userId
	                        );
}
