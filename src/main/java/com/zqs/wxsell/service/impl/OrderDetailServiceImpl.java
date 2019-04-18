package com.zqs.wxsell.service.impl;

import com.zqs.dao.BatchDaoImpl;
import com.zqs.wxsell.entity.OrderDetail;
import com.zqs.wxsell.service.OrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderDetailServiceImpl extends BatchDaoImpl<OrderDetail> implements OrderDetailService {
    @Override
    @Transactional
    public void batchInsert(List<OrderDetail> list) {
        super.batchInsert(list);
    }
}
