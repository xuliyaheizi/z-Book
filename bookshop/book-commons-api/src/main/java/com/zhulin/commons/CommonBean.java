package com.zhulin.commons;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:ZHULIN
 * @Description: 系统存储器
 */
@Component
public class CommonBean {

    /**
     * 登录存储器
     */
    public static Map<String, Object> loginMap = new HashMap<>();

    /**
     * 验证码存储器
     */
    public static Map<String, Integer> CodeMap = new HashMap<>();

    /**
     * 图书订单存储器
     */
    public static Map<Integer, Object> OrderBookMap = new HashMap<>();
}
