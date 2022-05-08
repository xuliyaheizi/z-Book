package com.zhulin.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhulin.dao.OrderInfoDao;
import com.zhulin.entities.OrderInfo;
import com.zhulin.enums.OrderStatus;
import com.zhulin.mapper.OrderInfoMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Author:ZHULIN
 * @Date:2022/4/30
 * @Description:
 */
@Repository
public class OrderInfoDaoImpl implements OrderInfoDao {
    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Override
    public int createOrder(OrderInfo orderInfo) {
        return orderInfoMapper.insert(orderInfo);
    }

    @Override
    public OrderInfo getOrderByOrderNo(String outTradeNo) {
        return orderInfoMapper.selectOne(new QueryWrapper<OrderInfo>().eq("ono", outTradeNo));
    }

    @Override
    public int updateStatusByOrderNo(String orderNo, OrderStatus orderStatus) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ono", orderNo);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOstatus(orderStatus.getType());

        return orderInfoMapper.update(orderInfo, queryWrapper);
    }
}
