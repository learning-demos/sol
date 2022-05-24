package com.github.dieselniu.wxshop.controller;

import com.github.dieselniu.wxshop.entity.PageResponse;
import com.github.dieselniu.wxshop.entity.Response;
import com.github.dieselniu.wxshop.exception.NotAuthorizationForShopException;
import com.github.dieselniu.wxshop.exception.ResourceNotFoundException;
import com.github.dieselniu.wxshop.generate.Goods;
import com.github.dieselniu.wxshop.service.GoodService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class GoodsController {
	private @Resource GoodService goodService;

	@PostMapping("/goods")
	public Response<Goods> createGoods(@RequestBody Goods goods, HttpServletResponse httpServletResponse) {
		clean(goods);
		httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
		try {
			return Response.of(goodService.createGoods(goods));
		} catch (NotAuthorizationForShopException exception) {
			httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return Response.of(exception.getMessage(), null);
		}
	}


	@DeleteMapping("goods/{id}")
	public Response<Goods> deleteGoods(@PathVariable("id") Long id, HttpServletResponse response) {
		try {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return Response.of(goodService.deleteById(id));
		} catch (NotAuthorizationForShopException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return Response.of(e.getMessage(), null);
		} catch (ResourceNotFoundException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return Response.of(e.getMessage(), null);
		}
	}


	@GetMapping("/goods")
	public @ResponseBody PageResponse<Goods> getGoods(@RequestParam("pageNum") Integer pageNum,
	                                                  @RequestParam("pageSize") Integer pageSize,
	                                                  @RequestParam(value = "shopId", required = false) Integer shopId) {

		return goodService.getGoods(pageNum, pageSize, shopId);
	}


	@PutMapping("/goods")
	public Response<Goods> updateGoods(Goods goods, HttpServletResponse response) {
		try {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return Response.of(goodService.updateGoods(goods));
		} catch (NotAuthorizationForShopException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return Response.of(e.getMessage(), null);
		} catch (ResourceNotFoundException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return Response.of(e.getMessage(), null);
		}
	}


	private void clean(Goods goods) {
		goods.setId(null);
		goods.setCreatedAt(new Date());
		goods.setUpdatedAt(new Date());
	}

}
