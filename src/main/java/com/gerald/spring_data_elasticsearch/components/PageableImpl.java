package com.gerald.spring_data_elasticsearch.components;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableImpl implements Pageable {
	private int pageNumber;
	
	private int pageSize;
	
	private Sort sort;
	
	public PageableImpl(com.gerald.spring_data_elasticsearch.Pageable p) {
		this.pageNumber = p.getIndex() - 1;
		this.pageSize = p.getSize();
	}
	
	private PageableImpl(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	@Override
	public int getPageNumber() {
		return pageNumber;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public int getOffset() {
		return pageNumber * pageSize;
	}

	@Override
	public Sort getSort() {
		return sort;
	}
	
	public void setSort(Sort sort) {
		this.sort = sort;
	}

	@Override
	public Pageable next() {
		return new PageableImpl(pageNumber + 1, pageSize);
	}

	@Override
	public Pageable previousOrFirst() {
		if(pageNumber == 0) {
			return this;
		} else {
			return new PageableImpl(pageNumber - 1, pageSize);
		}
	}

	@Override
	public Pageable first() {
		if(pageNumber == 0) {
			return this;
		} else {
			return new PageableImpl(0, pageSize);
		}
	}

	@Override
	public boolean hasPrevious() {
		return pageNumber > 0;
	}
	
}
