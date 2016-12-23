package com.gerald.spring_data_elasticsearch.domain;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class User {
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String firstName;
	
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
