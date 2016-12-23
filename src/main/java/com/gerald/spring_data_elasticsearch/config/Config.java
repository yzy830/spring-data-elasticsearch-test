package com.gerald.spring_data_elasticsearch.config;

import org.elasticsearch.client.ElasticsearchClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.TransportClientFactoryBean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.gerald.spring_data_elasticsearch.CustomElasticsearchTemplate;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.gerald.spring_data_elasticsearch.repository")
public class Config {
	@Bean
	public TransportClientFactoryBean clientFactory() {
		TransportClientFactoryBean clientFactory = new TransportClientFactoryBean();
		
		clientFactory.setClusterName("pxsj_release");
		clientFactory.setClusterNodes("192.168.0.101:9301,192.168.0.101:9302,192.168.0.101:9303");
		
		return clientFactory;
	}
	
	@Bean
	public ElasticsearchClient elasticsearchClient() {
		try {
			return clientFactory().getObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Bean
	public CustomElasticsearchTemplate elasticsearchTemplate() {
		try {
			return new CustomElasticsearchTemplate(clientFactory().getObject());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
