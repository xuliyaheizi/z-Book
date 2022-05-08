package com.zhulin.service;

import com.zhulin.dto.OrderBookDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Author:ZHULIN
 * @Date: 2022/4/30
 * @Description: 支付宝支付接口
 */
public interface AliPayService {

    /**
     * 统一支付
     * @param orderBookDto
     * @return
     */
    String tradeCreate(OrderBookDto orderBookDto);

    /**
     * 支付通知
     * @param params
     */
    @Transactional(rollbackFor = Exception.class)
    void processOrder(Map<String, String> params);
}
