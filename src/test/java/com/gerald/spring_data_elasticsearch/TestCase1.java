package com.gerald.spring_data_elasticsearch;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gerald.spring_data_elasticsearch.domain.Brand;
import com.gerald.spring_data_elasticsearch.domain.Goods;
import com.gerald.spring_data_elasticsearch.domain.Shop;
import com.gerald.spring_data_elasticsearch.domain.User;
import com.gerald.spring_data_elasticsearch.service.GoodsService;
import com.gerald.spring_data_elasticsearch.service.GoodsService.GoodsSearchResult;
import com.gerald.spring_data_elasticsearch.service.GoodsService.SearchParams;
import com.gerald.spring_data_elasticsearch.service.ShopService;
import com.gerald.spring_data_elasticsearch.service.ShopService.ShopSearchResult;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class TestCase1 {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Before
	public void before() {
		Shop shop1 = new Shop();
		shop1.setShopId(1L);
		shop1.setShopLocation(null);
		shop1.setShopName("shop-1");
		shop1.setShopShutdown(true);
		
		Shop shop2 = new Shop();
		shop2.setShopId(2L);
		shop2.setShopLocation(null);
		shop2.setShopName("shop-2");
		shop2.setShopShutdown(false);
		//天府广场
		shop2.setShopLocation(new GeoPoint(30.6639510000, 104.0714780000));
		
		Shop shop3 = new Shop();
		shop3.setShopId(3L);
		shop3.setShopLocation(null);
		shop3.setShopName("shop-3");
		shop3.setShopShutdown(false);
		
		User user1 = new User();
		user1.setFirstName("杨");
		user1.setLastName("棕源");
		
		User user2 = new User();
		user2.setFirstName("陈");
		user2.setLastName("可欣");
		shop3.setShopUsers(Arrays.asList(user1, user2));
		//软件园
		shop3.setShopLocation(new GeoPoint(30.5451680000, 104.0620180000));
		
		shopService.save(shop1);
		shopService.save(shop2);
		shopService.save(shop3);
		
		Shop shop4 = new Shop();
		shop4.setShopId(4L);
		shop4.setShopLocation(null);
		shop4.setShopName("shop-4");
		shop4.setShopShutdown(false);
		shop4.setShopLocation(new GeoPoint(30.5451680000, 104.0620180000));
		shopService.save(shop4);
		
		Brand sanxin = new Brand();
		sanxin.setDescription("三星公司。。。");
		sanxin.setName("三星");
		sanxin.setStartTime(new Date());
		
		Brand huawei = new Brand();
		huawei.setDescription("华为公司。。。");
		huawei.setName("华为");
		huawei.setStartTime(new Date());
		
		Brand leshi = new Brand();
		leshi.setDescription("乐视公司。。。");
		leshi.setName("乐视");
		leshi.setStartTime(new Date());
		
		Brand apple = new Brand();
		apple.setDescription("苹果公司。。。");
		apple.setName("Apple");
		apple.setStartTime(new Date());
		
		Brand vivo = new Brand();
		vivo.setDescription("VIVO是步步高旗下。。。");
		vivo.setName("VIVO");
		vivo.setStartTime(new Date());
		
		Goods goods1_1 = new Goods();
		goods1_1.setGoodId(1L);
		goods1_1.setGoodsBrand(huawei);
		goods1_1.setGoodsName("华为P9");
		goods1_1.setGoodsPrice(3000 * 100);
		goods1_1.setShopId("1");
		
		Goods goods1_2 = new Goods();
		goods1_2.setGoodId(2L);
		goods1_2.setGoodsBrand(sanxin);
		goods1_2.setGoodsName("三星Note9");
		goods1_2.setGoodsPrice(4000 * 100);
		goods1_2.setShopId("1");
		
		Goods goods2_1 = new Goods();
		goods2_1.setGoodId(3L);
		goods2_1.setGoodsBrand(sanxin);
		goods2_1.setGoodsName("三星Galaxy 3");
		goods2_1.setGoodsPrice(2500 * 100);
		goods2_1.setShopId("2");
		
		Goods goods2_2 = new Goods();
		goods2_2.setGoodId(4L);
		goods2_2.setGoodsBrand(leshi);
		goods2_2.setGoodsName("乐视生态手机V1");
		goods2_2.setGoodsPrice(1500 * 100);
		goods2_2.setShopId("2");
		
		Goods goods3_1 = new Goods();
		goods3_1.setGoodId(5L);
		goods3_1.setGoodsBrand(apple);
		goods3_1.setGoodsName("Apple7 Plus");
		goods3_1.setGoodsPrice(7000 * 100);
		goods3_1.setShopId("3");
		
		Goods goods3_2 = new Goods();
		goods3_2.setGoodId(6L);
		goods3_2.setGoodsBrand(leshi);
		goods3_2.setGoodsName("乐视生态手机V2");
		goods3_2.setGoodsPrice(1800 * 100);
		goods3_2.setShopId("3");
		
		Goods goods3_3 = new Goods();
		goods3_3.setGoodId(7L);
		goods3_3.setGoodsBrand(vivo);
		goods3_3.setGoodsName("VIVO 2700w柔光自拍");
		goods3_3.setGoodsPrice(2000 * 100);
		goods3_3.setShopId("3");
		
		goodsService.save(goods3_3);
		goodsService.save(goods3_2);
		goodsService.save(goods3_1);
		goodsService.save(goods2_2);
		goodsService.save(goods2_1);
		goodsService.save(goods1_2);
		goodsService.save(goods1_1);
	}
	
//	@After
//	public void after() {
//		elasticsearchTemplate.deleteIndex("test_shopping");
//	}
	
	@Test
	public void testGoods() {
		try {
			SearchParams params = new SearchParams();
//			params.setGoodsName("乐视");
			params.setPriceAsc(false);
			
			Pageable pageable = new Pageable();
			pageable.setIndex(2);
			pageable.setSize(2);
			
			GoodsSearchResult result = goodsService.search(params, pageable);
			
			System.out.println("===================商品=====================");			
			for(Goods goods : result.getContent().getContent()) {
				System.out.println("shop = " + goods.getShopId() + ", goods_id = " + goods.getGoodId() + ", name = " + goods.getGoodsName() + ", price = " + goods.getGoodsPrice() + ", brand = " + goods.getGoodsBrand().getName());
			}
			
			System.out.println("===================分类=====================");
			for(String brand : result.getBrands()) {
				System.out.println(brand);
			}
		} catch(Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testShopByGoodsCount() {
		try {
			List<Shop> shops = shopService.queryShopWithGoodsNumberGreateThan(3);
			
			System.out.println("===================商品数量查询商铺=====================");	
			for(Shop shop : shops) {
				System.out.println("id = " + shop.getShopId() + ", name = " + shop.getShopName());
			}
		} catch(Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testShopGeoQuery() {
		try {
			System.out.println("===================距离查询商铺=====================");	
			ShopSearchResult shops = shopService.queryShopByLocation(new GeoPoint(30.5607410000,104.0761780000), 20, false);
			
			for(ShopSearchResult.Item item : shops.getItems()) {
				System.out.println("id = " + item.getShop().getShopId() 
						         + ", name = " + item.getShop().getShopName()
						         + ", distance = " + item.getDistance());
			}
		} catch(Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
}
