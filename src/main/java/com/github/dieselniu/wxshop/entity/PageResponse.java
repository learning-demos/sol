package com.github.dieselniu.wxshop.entity;

import java.util.List;

public class PageResponse<T> {
	private int pageNum;
	private int pageSize;
	private int totalPage;
	private List<T> data;


	public PageResponse() {
	}

	public static <T> PageResponse<T> pageData(int pageNum, int pageSize, int totalPage, List<T> data) {
		PageResponse<T> result = new PageResponse<>();
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalPage(totalPage);
		result.setData(data);
		return result;
	}

	public int getPageNum() {
		return pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public List<T> getData() {
		return data;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
