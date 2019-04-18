package com.zqs.wxsell.repository;

import com.zqs.wxsell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    //根据类目和编号查询商品
    List<ProductInfo> findByProductStatusAndCategoryTypeIn(Integer status,List<Integer> categoryType);
}
