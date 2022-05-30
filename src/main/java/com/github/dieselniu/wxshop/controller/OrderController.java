package com.github.dieselniu.wxshop.controller;

import com.github.dieselniu.wxshop.api.OrderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {
	@DubboReference(version = "1.0.0")
	private OrderService orderService;

	@GetMapping("/rpc")
	public String testRPC() {
		orderService.placeOrder(1, 2);
		return "niuweizhe";
	}
}
