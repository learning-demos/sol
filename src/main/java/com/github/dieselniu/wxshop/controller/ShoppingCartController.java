package com.github.dieselniu.wxshop.controller;

import com.github.dieselniu.wxshop.entity.PageResponse;
import com.github.dieselniu.wxshop.entity.Response;
import com.github.dieselniu.wxshop.entity.ShoppingCartOutput;
import com.github.dieselniu.wxshop.service.ShoppingCartService;
import com.github.dieselniu.wxshop.service.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.ws.http.HTTPException;

@RestController
@RequestMapping("/api")
public class ShoppingCartController {
	private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);
	private @Resource ShoppingCartService shoppingCartService;


	@PostMapping("/shoppingCart")
	public Response<ShoppingCartOutput> addToShoppingCart(@RequestBody AddToShoppingCartInput input) {
		logger.debug("User {} is adding shopping cart {} ", UserContext.getCurrentUser().getId(),
			input);
		try {
			return Response.of(shoppingCartService.addToShoppingCart(input, UserContext.getCurrentUser().getId()));
		} catch (HTTPException exception) {
			return Response.of(exception.getMessage(), null);
		}
	}

	@GetMapping("/shoppingCart")
	public PageResponse<ShoppingCartOutput> getShoppingCart(
		@RequestParam("pageNum") int pageNumber,
		@RequestParam("pageSize") int pageSize) {
		return shoppingCartService.getShoppingCartOfUser(UserContext.getCurrentUser().getId(),
			pageNumber, pageSize);
	}


	@DeleteMapping("/shoppingCart/{id}")
	public Response<ShoppingCartOutput> deleteGoodsInShoppingCart(@PathVariable("id") Long goodsId) {
		try {
			return Response.of(shoppingCartService.deleteGoodsInShoppingCart(goodsId, UserContext.getCurrentUser().getId()));
		} catch (HTTPException exception) {
			return Response.of(exception.getMessage(), null);
		}
	}

}
