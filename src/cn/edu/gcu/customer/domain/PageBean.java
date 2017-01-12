package cn.edu.gcu.customer.domain;

import java.util.List;

public class PageBean<T> {
	private int pageCode;//当前页码page code.
	//private int totalPage;//总页数total page.
	private int totalRecord;//总记录数total record.
	private int pageSize;//每页记录数page size.
	private List<T> beanList;//当前页的记录.
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/*
	 * 计算总页数
	 */
	public int getTotalPage() {
		int totalPage=totalRecord/pageSize;
		return totalRecord%pageSize==0 ? totalPage : totalPage+1;
	}


	public int getPageCode() {
		return pageCode;
	}


	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}


	public int getTotalRecord() {
		return totalRecord;
	}


	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public List<T> getBeanList() {
		return beanList;
	}


	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}

}
