package com.youruan.dentistry.core.base.query;

import java.util.List;

public class Pagination<T> {
	
	private int rows;						// 总数据行数
	private List<T> data;					// 当前页数据

	public boolean hasRows(){
		return rows != 0;
	}

	public Pagination(int rows, List<T> data) {
		this.rows = rows;
		this.data = data;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
