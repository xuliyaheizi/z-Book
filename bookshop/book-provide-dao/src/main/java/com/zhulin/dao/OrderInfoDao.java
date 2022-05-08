package com.zhulin.dao;

import com.zhulin.entities.OrderInfo;
import com.zhulin.enums.OrderStatus;

/**
 * @Author:ZHULIN
 * @Date: 2022/4/30
 * @Description:
 */
public interface OrderInfoDao {

    /**
     * 创建订单
     * @param orderInfo
     * @return
     */
    int createOrder(OrderInfo orderInfo);

    /**
     * 根据订单编号获取订单信息
     * @param outTradeNo
     * @return
     */
    OrderInfo getOrderByOrderNo(String outTradeNo);

    /**
     * 修改订单状态
     * @param orderNo
     * @param orderStatus
     * @return
     */
    int updateStatusByOrderNo(String orderNo, OrderStatus orderStatus);
}
