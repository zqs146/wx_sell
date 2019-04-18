package com.zqs.wxsell;

import com.zqs.wxsell.entity.ProductCategory;
import com.zqs.wxsell.repository.ProductCategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellProjectApplicationTests {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Test
	public void contextLoads() {
//		List<ProductCategory> all = productCategoryRepository.findAll();
//		all.stream().forEach(System.out::println);
		ProductCategory productCategory = productCategoryRepository.queryByCategoryIdAndCategoryType(2);
		System.out.println(productCategory);
	}

}
