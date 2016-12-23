package com.gerald.spring_data_elasticsearch;

import java.util.List;

public class Page<T> {
	private long total;
	
	private List<T> content;
	
	private Page(long total, List<T> content) {
		this.total = total;
		this.content = content;
	}

	public long getTotal() {
		return total;
	}

	public List<T> getContent() {
		return content;
	}
	
	public static <T> Page<T> newInstance(long total, List<T> content) {
		return new Page<T>(total, content);
	}
}
