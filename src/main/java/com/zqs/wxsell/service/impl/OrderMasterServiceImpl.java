package com.zqs.wxsell.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zqs.exception.CustomException;
import com.zqs.wxsell.common.OrderEnums;
import com.zqs.wxsell.common.PayEnums;
import com.zqs.wxsell.common.ResultEnums;
import com.zqs.wxsell.common.ResultResponse;
import com.zqs.wxsell.dto.OrderDetailDto;
import com.zqs.wxsell.dto.OrderMasterDto;
import com.zqs.wxsell.entity.OrderDetail;
import com.zqs.wxsell.entity.OrderMaster;
import com.zqs.wxsell.entity.ProductInfo;
import com.zqs.wxsell.repository.OrderMasterRepository;
import com.zqs.wxsell.service.OrderDetailService;
import com.zqs.wxsell.service.OrderMasterService;
import com.zqs.wxsell.service.ProductInfoService;
import com.zqs.wxsell.utils.BigDecimalUtil;
import com.zqs.wxsell.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    public ResultResponse insertOrder(OrderMasterDto orderMasterDto) {
        //取出订单项
        List<OrderDetailDto> items = orderMasterDto.getItems();
        //创建集合来存储OrderDetail
        List<OrderDetail> orderDetailList = Lists.newArrayList();
        //初始化订单的总金额
        BigDecimal totalPrice = new BigDecimal("0");
        //遍历订单项，获取商品详情
        for (OrderDetailDto orderDetailDto:items) {
            //查询订单
            ResultResponse<ProductInfo> resultResponse = productInfoService.queryByid(orderDetailDto.getProductId());
            //判断resultResponse的code即可
            if (resultResponse.getCode() == ResultEnums.FAIL.getCode()){
                throw new CustomException(resultResponse.getMsg());
            }
            //获取得到商品
            ProductInfo productInfo = resultResponse.getData();
            //比较库存
            if (productInfo.getProductStock() <orderDetailDto.getProductQuantity()){
                throw new CustomException(ResultEnums.PRODUCT_NOT_ENOUGH.getMsg());
            }
            //创建订单项
            OrderDetail orderDetail = OrderDetail.builder().detailId(IDUtils.createIdbyUUID()).productIcon(productInfo.getProductIcon())
                    .productId(orderDetailDto.getProductId()).productName(productInfo.getProductName())
                    .productPrice(productInfo.getProductPrice()).productQuantity(orderDetailDto.getProductQuantity())
                    .build();
            orderDetailList.add(orderDetail);
            //减少库存
            productInfo.setProductStock(productInfo.getProductStock() - orderDetailDto.getProductQuantity());
            //更新商品数据
            productInfoService.updateProduct(productInfo);
            //计算价格
            totalPrice = BigDecimalUtil.add(totalPrice,BigDecimalUtil.multi(productInfo.getProductPrice(),orderDetailDto.getProductQuantity()));
        }
        //生成订单
        String orderId = IDUtils.createIdbyUUID();
        //构建订单信息
        OrderMaster orderMaster = OrderMaster.builder().buyerAddress(orderMasterDto.getAddress()).buyerName(orderMasterDto.getName())
                .buyerOpenid(orderMasterDto.getOpenid()).orderStatus(OrderEnums.NEW.getCode())
                .payStatus(PayEnums.WAIT.getCode()).buyerPhone(orderMasterDto.getPhone())
                .orderId(orderId).orderAmount(totalPrice).build();

        //将订单id设置到订单项中
        List<OrderDetail> orderDetails = orderDetailList.stream().map(orderDetail -> {
            orderDetail.setOrderId(orderId);
            return orderDetail;
        }).collect(Collectors.toList());
        //批量插入订单项
        orderDetailService.batchInsert(orderDetails);
        //插入订单
        orderMasterRepository.save(orderMaster);
        HashMap<String, String> map = Maps.newHashMap();
        map.put("orderId",orderId);
        return ResultResponse.success(map);
    }
}
