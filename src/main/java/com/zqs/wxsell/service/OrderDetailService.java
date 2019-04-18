package com.zqs.wxsell.service;

import com.zqs.wxsell.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    //批量插入
    void batchInsert(List<OrderDetail> list);

}
