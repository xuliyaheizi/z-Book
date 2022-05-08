package com.zhulin.service;

import com.zhulin.dto.*;

import java.util.Map;

/**
 * @Author:ZHULIN
 * @Date:2022/4/30
 * @Description: 购物车
 */
public interface CartService {

    /**
     * 添加购物车
     * @param cartDto
     * @return
     */
    boolean addNewCart(CartDto cartDto);

    /**
     * 查看购物车
     * @param uid
     * @return
     */
    Map<String, Object> showCart(int uid);

    /**
     * 修改购物车数量
     * @param updateCartDto
     * @return
     */
    boolean updateCartBookNum(UpdateCartDto updateCartDto);

    /**
     * 删除单个图书
     * @param deleteSignalDto
     * @return
     */
    boolean deleteSignal(DeleteSignalDto deleteSignalDto);

    /**
     * 删除多个图书
     * @param deleteMultiple
     * @return
     */
    boolean deleteMultiple(DeleteMultiple deleteMultiple);

    /**
     * 获取存储器中用户的订单图书信息
     * @param uid
     * @return
     */
    OrderBookDto getOrderBook(int uid);
}
