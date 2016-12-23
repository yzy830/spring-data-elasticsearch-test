package com.gerald.spring_data_elasticsearch.domain;

import java.util.Date;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class Brand {
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String name;
	
	@Field(type = FieldType.String, index = FieldIndex.no)
	private String description;
	
	@Field(type = FieldType.Date, index = FieldIndex.no)
	private Date startTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
}
