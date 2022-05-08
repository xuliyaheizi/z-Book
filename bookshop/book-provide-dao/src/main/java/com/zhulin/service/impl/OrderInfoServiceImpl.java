package com.zhulin.service.impl;

import com.zhulin.dao.OrderInfoDao;
import com.zhulin.dto.OrderBookDto;
import com.zhulin.entities.OrderInfo;
import com.zhulin.enums.OrderStatus;
import com.zhulin.enums.PayType;
import com.zhulin.service.OrderInfoService;
import com.zhulin.utils.OrderNoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:ZHULIN
 * @Date:2022/4/30
 * @Description: 订单
 */
@Service
@Slf4j
public class OrderInfoServiceImpl implements OrderInfoService {
    @Resource
    private OrderInfoDao orderInfoDao;

    @Override
    public OrderInfo createOrder(OrderBookDto orderBookDto) {
        OrderInfo orderInfo = new OrderInfo();
        //用户id
        orderInfo.setUid(orderBookDto.getUid());
        //订单金额
        orderInfo.setOtotalprice(orderBookDto.getActualPrice());
        //获取订单号
        orderInfo.setOno(OrderNoUtils.getOrderNo());
        orderInfo.setOstatus(OrderStatus.NOTPAY.getType());
        orderInfo.setOpaytype(PayType.ALIPAY.getType());
        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String createTime = simpleDateFormat.format(new Date());
        orderInfo.setOcreatetime(createTime);
        orderInfo.setOtitle(createTime);

        int result = orderInfoDao.createOrder(orderInfo);
        if (result == 1) {
            return orderInfo;
        }
        return null;
    }

    @Override
    public OrderInfo getOrderByOrderNo(String outTradeNo) {
        return orderInfoDao.getOrderByOrderNo(outTradeNo);
    }

    @Override
    public boolean updateStatusByOrderNo(String orderNo, OrderStatus orderStatus) {
        log.info("更新订单状态 ===> {}", orderStatus.getType());

        int result = orderInfoDao.updateStatusByOrderNo(orderNo, orderStatus);
        if (result > 0) {
            return true;
        }
        return false;
    }
}
