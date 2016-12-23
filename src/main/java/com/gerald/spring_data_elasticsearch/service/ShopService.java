package com.gerald.spring_data_elasticsearch.service;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.gerald.spring_data_elasticsearch.CustomElasticsearchTemplate;
import com.gerald.spring_data_elasticsearch.Pageable;
import com.gerald.spring_data_elasticsearch.components.PageableImpl;
import com.gerald.spring_data_elasticsearch.domain.Shop;
import com.gerald.spring_data_elasticsearch.repository.ShopRepository;
import com.gerald.spring_data_elasticsearch.service.ShopService.ShopSearchResult.Item;

@Service
public class ShopService {
	@Autowired
	private ShopRepository shopRepository;
	
	@Autowired
	private CustomElasticsearchTemplate elasticTemplate;
	
	public void save(Shop shop) {
		shopRepository.save(shop);
	}
	
	public List<Shop> queryShopWithGoodsNumberGreateThan(int count) {
		QueryBuilder query = QueryBuilders.hasChildQuery("test_goods", QueryBuilders.matchAllQuery()).minChildren(count);
		List<Shop> result = new ArrayList<Shop>();
		
		for(Shop shop : shopRepository.search(query)) {
			result.add(shop);
		}
		
		return result;
	}
	
	public static class ShopSearchResult {
		public static class Item {
			private Shop shop;
			
			private double distance;
			
			public Item(Shop shop, double distance) {
				this.shop = shop;
				this.distance = distance;
			}

			public Shop getShop() {
				return shop;
			}

			public double getDistance() {
				return distance;
			}
		}
		
		private List<Item> items = new ArrayList<Item>();
		
		public List<Item> getItems() {
			return items;
		}
	}
	
	public ShopSearchResult queryShopByLocation(GeoPoint point, int dist, boolean distAsc) {
		QueryBuilder query = QueryBuilders.boolQuery().filter(QueryBuilders.geoDistanceQuery("shopLocation")
																				.distance(Integer.toString(dist) + "km")
																				.lat(point.getLat())
																				.lon(point.getLon()))
													  .filter(QueryBuilders.termQuery("shopShutdown", false));
		
		SortBuilder sort = SortBuilders.geoDistanceSort("shopLocation")
											.point(point.getLat(), point.getLon())
											.order(distAsc? SortOrder.ASC : SortOrder.DESC);
		
		
		NativeSearchQueryBuilder search = new NativeSearchQueryBuilder().withQuery(query).withSort(sort);
		
		return elasticTemplate.query(search.build(), new ResultsExtractor<ShopSearchResult>() {

			@Override
			public ShopSearchResult extract(SearchResponse response) {
				ShopSearchResult result = new ShopSearchResult();
				
				Pageable pageable = new Pageable();
				pageable.setIndex(0);
				pageable.setSize(10);
				List<Shop> shops = elasticTemplate.getResultsMapper().mapResults(response, Shop.class, new PageableImpl(pageable)).getContent();
				
				for(int i = 0; i < response.getHits().getHits().length; ++i) {
					Shop shop = shops.get(i);
					
					double distance = Double.valueOf(response.getHits().getHits()[i].getSortValues()[0].toString()) / 1000;
					
					result.items.add(new Item(shop, distance));
				}
				
				return result;
			}
			
		});
	}
}
