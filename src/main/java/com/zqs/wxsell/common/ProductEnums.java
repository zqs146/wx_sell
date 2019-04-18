package com.zqs.wxsell.common;

import lombok.Getter;

@Getter
public enum ProductEnums {

    PRODUCT_NOT_ENOUGH(1,"商品库存不足");

    private Integer code;
    private String msg;

    ProductEnums(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
