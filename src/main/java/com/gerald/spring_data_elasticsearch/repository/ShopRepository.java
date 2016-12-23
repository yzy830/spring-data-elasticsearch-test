package com.gerald.spring_data_elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.gerald.spring_data_elasticsearch.domain.Shop;

public interface ShopRepository extends ElasticsearchRepository<Shop, Long> {

}
