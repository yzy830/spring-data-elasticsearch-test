package com.gerald.spring_data_elasticsearch.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.gerald.spring_data_elasticsearch.CustomElasticsearchTemplate;
import com.gerald.spring_data_elasticsearch.Page;
import com.gerald.spring_data_elasticsearch.Pageable;
import com.gerald.spring_data_elasticsearch.components.PageableImpl;
import com.gerald.spring_data_elasticsearch.domain.Goods;
import com.gerald.spring_data_elasticsearch.repository.GoodsRepository;

@Service
public class GoodsService {
	@Autowired
	private GoodsRepository goodsRepository;
	
	@Autowired
	private CustomElasticsearchTemplate elasticTemplate;
	
	public static class SearchParams {
		private String goodsName;
		
		private boolean priceAsc;

		public String getGoodsName() {
			return goodsName;
		}

		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}

		public boolean isPriceAsc() {
			return priceAsc;
		}

		public void setPriceAsc(boolean priceAsc) {
			this.priceAsc = priceAsc;
		}
	}
	
	public static class GoodsSearchResult {
		//这个地方需要测试两种情况，返回Page和返回List
		private Page<Goods> content;
		
		private List<String> brands;

		public Page<Goods> getContent() {
			return content;
		}

		public void setContent(Page<Goods> content) {
			this.content = content;
		}

		public List<String> getBrands() {
			return brands;
		}

		public void setBrands(List<String> brands) {
			this.brands = brands;
		}
	}
	
	public GoodsSearchResult search(SearchParams params, final Pageable pageable) {			
		BoolQueryBuilder builder = QueryBuilders.boolQuery()
													.must(QueryBuilders.hasParentQuery("test_shop", 
														 					QueryBuilders.termQuery("shopShutdown", false)))
									        ;
		
		if(StringUtils.isNotEmpty(params.getGoodsName())) {
			builder.must(QueryBuilders.matchQuery("goodsName", params.getGoodsName()));
		}
		
		NativeSearchQueryBuilder searchBuilder = new NativeSearchQueryBuilder().withQuery(builder);
		
		if(params.isPriceAsc()) {
			searchBuilder.withSort(SortBuilders.fieldSort("goodsPrice").order(SortOrder.ASC));
		} else {
			searchBuilder.withSort(SortBuilders.fieldSort("goodsPrice").order(SortOrder.DESC));
		}
		
		searchBuilder.addAggregation(AggregationBuilders.terms("brand").field("goodsBrand.name"));
		searchBuilder.withPageable(new PageableImpl(pageable));
		
		return elasticTemplate.query(searchBuilder.build(), new ResultsExtractor<GoodsSearchResult>() {

			@Override
			public GoodsSearchResult extract(SearchResponse response) {				
				org.springframework.data.domain.Page<Goods> page = elasticTemplate.getResultsMapper()
						.mapResults(response, Goods.class, new PageableImpl(pageable));
				
				Terms brands = response.getAggregations().get("brand");
				
				GoodsSearchResult result = new GoodsSearchResult();
				result.setContent(Page.newInstance(page.getTotalElements(), page.getContent()));
				
				List<String> brandNames = new ArrayList<String>();
				for(Terms.Bucket bucket : brands.getBuckets()) {
					brandNames.add(bucket.getKeyAsString());
				}
				
				result.setBrands(brandNames);
				
				return result;
			}
			
		});
	}
	
	public void save(Goods goods) {
		goodsRepository.save(goods);
	}
}
