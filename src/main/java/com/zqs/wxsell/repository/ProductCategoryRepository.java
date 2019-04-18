package com.zqs.wxsell.repository;

import com.zqs.wxsell.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> { //第一个参数是实体类名称，第二个参数是主键类型

    @Override
    List<ProductCategory> findAll();

    @Query(value = "select * from product_category where category_id = ?1",nativeQuery = true)
    ProductCategory queryByCategoryIdAndCategoryType(Integer id);
}
