package com.zqs.wxsell.service;

import com.zqs.wxsell.common.ResultResponse;
import com.zqs.wxsell.dto.OrderMasterDto;

public interface OrderMasterService {

    ResultResponse insertOrder(OrderMasterDto orderMasterDto);

}
