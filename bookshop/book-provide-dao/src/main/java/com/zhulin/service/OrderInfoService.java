package com.zhulin.service;

import com.zhulin.dto.OrderBookDto;
import com.zhulin.entities.OrderInfo;
import com.zhulin.enums.OrderStatus;

/**
 * @Author:ZHULIN
 * @Date: 2022/4/30
 * @Description: 订单
 */
public interface OrderInfoService {

    /**
     * 创建订单
     * @param orderBookDto
     * @return
     */
    OrderInfo createOrder(OrderBookDto orderBookDto);

    /**
     * 根据订单编号获取订单
     * @param orderNo
     * @return
     */
    OrderInfo getOrderByOrderNo(String orderNo);

    /**
     * 修改订单状态
     * @param orderNo
     * @param orderStatus
     * @return
     */
    boolean updateStatusByOrderNo(String orderNo, OrderStatus orderStatus);
}
