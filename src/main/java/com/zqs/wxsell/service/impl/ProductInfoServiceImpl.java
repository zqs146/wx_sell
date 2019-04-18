package com.zqs.wxsell.service.impl;

import com.zqs.exception.CustomException;
import com.zqs.wxsell.common.ResultEnums;
import com.zqs.wxsell.common.ResultResponse;
import com.zqs.wxsell.dto.ProductCategoryDto;
import com.zqs.wxsell.dto.ProductInfoDto;
import com.zqs.wxsell.entity.ProductCategory;
import com.zqs.wxsell.entity.ProductInfo;
import com.zqs.wxsell.repository.ProductCategoryRepository;
import com.zqs.wxsell.repository.ProductInfoRepository;
import com.zqs.wxsell.service.ProductInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ResultResponse queryList() {
        //查询所有分类
        List<ProductCategory> all = productCategoryRepository.findAll();
        //将all转换成dto
        List<ProductCategoryDto> productCategoryDtoList =
                all.stream().map(ProductCategory -> ProductCategoryDto.build(ProductCategory)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(all)){
            return ResultResponse.fail();
        }
        //获取类目编号集合
        List<Integer> typeList =
                productCategoryDtoList.stream().map(productCategoryDto -> productCategoryDto.getCategoryType()).collect(Collectors.toList());

        //根据typeList查询商品列表
        List<ProductInfo> productInfoList = productInfoRepository.findByProductStatusAndCategoryTypeIn(ResultEnums.PRODUCT_UP.getCode(), typeList);

        //对productCategoryDtoList集合进行遍历，取出每个商品的类目编号，设置到对应的目录中
        //将productIndo设置到foods中
        //过滤：不同的type进行不同的封装
        //将productInfoDto转换成Dto
        List<ProductCategoryDto> productCategoryDtos = productCategoryDtoList.stream().map(productCategoryDto -> {
            productCategoryDto.setProductInfoDtoList(productInfoList.stream()
                    .filter(productInfo -> productInfo.getCategoryType() == productCategoryDto.getCategoryType())
                    .map(productInfo -> ProductInfoDto.build(productInfo)).collect(Collectors.toList()));
            return productCategoryDto;
        }).collect(Collectors.toList());
        return ResultResponse.success(productCategoryDtos);
    }

    @Override
    public ResultResponse<ProductInfo> queryByid(String productId) {
        if (StringUtils.isBlank(productId)){
            return ResultResponse.fail(ResultEnums.PARAM_ERROR.getMsg());
        }
        Optional<ProductInfo> byId = productInfoRepository.findById(productId);
        if (!byId.isPresent()){
            return ResultResponse.fail(ResultEnums.NOT_EXITS.getMsg());
        }
        ProductInfo productInfo = byId.get();
        if (productInfo.getProductStatus() == ResultEnums.PRODUCT_DOWN.getCode()){
            return ResultResponse.fail(ResultEnums.PRODUCT_DOWN.getMsg());
        }
        return ResultResponse.success(productInfo);
    }

    @Override
    public void updateProduct(ProductInfo productInfo) {
        productInfoRepository.save(productInfo);
    }
}
