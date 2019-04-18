package com.zqs.wxsell.common;

import lombok.Getter;

@Getter
public enum  OrderEnums {

    NEW(0,"新建订单"),
    FINSH(1,"已完成订单"),
    CANCEL(2,"已取消"),
    OPENID_ERROR(4,"订单openid输入有误"),
    ORDER_NOT_EXITS(5,"订单不存在"),
    FINSH_CANCEL(6,"订单已经完成,或者已经取消,无法取消"),
    AMOUNT_CHECK_ERROR(7,"微信支付订单金额与数据库查询的不一致");

    private Integer code;
    private String msg;

    OrderEnums(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
