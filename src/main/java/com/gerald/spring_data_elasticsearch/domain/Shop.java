package com.gerald.spring_data_elasticsearch.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Document(indexName = "test_shopping", type = "test_shop", shards = 1, replicas = 3)
public class Shop {
	@Id
	private Long shopId;
	
	@Field(type = FieldType.String, analyzer = "ik")
	private String shopName;
	
	private boolean isShopShutdown;
	
	@Field(type = FieldType.Nested)
	private List<User> shopUsers;
	
	private GeoPoint shopLocation;
	
	@Field(type = FieldType.String, index = FieldIndex.no)
	private String strtest;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public boolean isShopShutdown() {
		return isShopShutdown;
	}

	public void setShopShutdown(boolean isShopShutdown) {
		this.isShopShutdown = isShopShutdown;
	}

	public List<User> getShopUsers() {
		return shopUsers;
	}

	public void setShopUsers(List<User> shopUsers) {
		this.shopUsers = shopUsers;
	}

	public GeoPoint getShopLocation() {
		return shopLocation;
	}

	public void setShopLocation(GeoPoint shopLocation) {
		this.shopLocation = shopLocation;
	}
}
