package com.zqs.wxsell.service;

import com.zqs.wxsell.common.ResultResponse;
import com.zqs.wxsell.entity.ProductInfo;

public interface ProductInfoService {

    ResultResponse queryList();

    ResultResponse<ProductInfo> queryByid(String productId);

    void updateProduct(ProductInfo productInfo);
}
