package com.github.dieselniu.wxshop.entity;

import lombok.Getter;

@Getter
public class CreateGoodsInput {
	private Long shopId;
	private String name;
	private String description;
	private String imgUrl;
	private Long price;
	private Integer stock;
	private String status;
}
