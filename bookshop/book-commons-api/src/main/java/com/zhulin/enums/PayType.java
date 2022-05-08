package com.zhulin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author:ZHULIN
 * @Date:2022/4/30
 * @Description: 支付方式
 */
@Getter
@AllArgsConstructor
public enum PayType {
    /**
     * 微信
     */
    WXPAY("微信"),

    /**
     * 支付宝
     */
    ALIPAY("支付宝");

    /**
     * 类型
     */
    private final String type;
}
