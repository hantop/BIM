package com.zzrbi.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具
 * @author sihang
 *
 */
public class Pagination<E> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4903920699956880372L;
	/**
	 * 页码
	 */
	private int page;
	/**
	 * 数据总数
	 */
	private int total;
	/**
	 * 分页数据
	 */
	private List<E> rows = new ArrayList<E>();

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

}
