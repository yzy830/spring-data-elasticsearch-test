package com.gerald.spring_data_elasticsearch.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Parent;

@Document(indexName = "test_shopping", type = "test_goods", shards = 1, replicas = 3)
public class Goods {
	@Id
	@Field(type = FieldType.Auto)
	private Long goodId;
	
	@Field(type = FieldType.String, analyzer = "ik")
	private String goodsName;
	
	@Field(type = FieldType.Integer)
	private Integer goodsPrice;
	
	@Parent(type = "test_shop")
	@Field(type = FieldType.String, index = FieldIndex.no)
	private String shopId;
	
	@Field(type = FieldType.Object)
	private Brand goodsBrand;

	public Long getGoodId() {
		return goodId;
	}

	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Integer goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public Brand getGoodsBrand() {
		return goodsBrand;
	}

	public void setGoodsBrand(Brand goodsBrand) {
		this.goodsBrand = goodsBrand;
	}
}
