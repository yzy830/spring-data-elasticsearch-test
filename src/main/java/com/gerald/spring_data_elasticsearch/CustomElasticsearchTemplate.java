package com.gerald.spring_data_elasticsearch;

import org.elasticsearch.client.Client;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsMapper;

public class CustomElasticsearchTemplate extends ElasticsearchTemplate {

	public CustomElasticsearchTemplate(Client client) {
		super(client);
	}

	@Override
	public ResultsMapper getResultsMapper() {
		return super.getResultsMapper();
	}
}
