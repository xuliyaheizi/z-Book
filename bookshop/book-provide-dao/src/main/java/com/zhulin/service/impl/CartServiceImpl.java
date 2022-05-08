package com.zhulin.service.impl;

import com.zhulin.commons.CommonBean;
import com.zhulin.dto.*;
import com.zhulin.entities.Book;
import com.zhulin.service.BookService;
import com.zhulin.service.CartService;
import com.zhulin.utils.RedisUtils;
import com.zhulin.vo.CartBookVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZHULIN
 * @Date:2022/4/30
 * @Description:
 */
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private BookService bookService;

    @Override
    public boolean addNewCart(CartDto cartDto) {
        //获取购物车信息
        Map<Object, Object> cartMap = redisUtils.hmget(cartDto.getUid().toString());
        //清空缓存
        redisUtils.del(String.valueOf(cartDto.getUid()));
        //添加新购物车
        Map<String, Object> cartDtoMap = new HashMap<>(16);
        //当购物车不为空时，循环列表完成数量增加或者添加新的图书
        if (cartMap != null && cartMap.size() > 0) {
            cartMap.forEach((k, v) -> {
                //存同一种书  实现数量增加
                if (cartDto.getBid().toString().equals(k)) {
                    CartDto oldCartDto = (CartDto) v;
                    cartDto.setNum(cartDto.getNum() + oldCartDto.getNum());
                } else {
                    cartDtoMap.put(k.toString(), v);
                }
            });
        }
        cartDtoMap.put(cartDto.getBid().toString(), cartDto);
        return redisUtils.hmset(cartDto.getUid().toString(), cartDtoMap);
    }

    @Override
    public Map<String, Object> showCart(int uid) {
        Map<Object, Object> cartMap = redisUtils.hmget(String.valueOf(uid));
        List<CartBookVo> cartBookVoList = new ArrayList<>();
        Map<String, Object> cartBookMap = new HashMap<>(16);
        //购物车数量
        int cartCount = 0;
        //购物车总价格
        double totalPrice = 0;
        //循环购物车 获取图书信息
        for (Map.Entry<Object, Object> entry : cartMap.entrySet()) {
            int bookId = Integer.parseInt(entry.getKey().toString());
            //查询图书信息
            Book bookInCart = bookService.getBookById(bookId);
            CartDto cartDto = (CartDto) entry.getValue();
            cartCount += cartDto.getNum();
            //计算单品总价 保留两位小数
            double itemPriceId = bookInCart.getBprice() * cartDto.getNum();
            BigDecimal bg = new BigDecimal(itemPriceId);
            itemPriceId = bg.setScale(2, RoundingMode.valueOf(2)).doubleValue();
            totalPrice += itemPriceId;
            //将信息保存到 cartBookVo中 并添加进list
            CartBookVo cartBookVo = new CartBookVo(bookInCart, cartDto.getNum(), itemPriceId);
            cartBookVoList.add(cartBookVo);
        }
        BigDecimal bigDecimal = new BigDecimal(totalPrice);
        totalPrice = bigDecimal.setScale(2, RoundingMode.valueOf(2)).doubleValue();
        cartBookMap.put("CartCount", cartCount);
        cartBookMap.put("CartTotalPrice", totalPrice);
        cartBookMap.put("CartBookList", cartBookVoList);
        return cartBookMap;
    }

    @Override
    public boolean updateCartBookNum(UpdateCartDto updateCartDto) {
        Map<String, Object> cartBookMap = new HashMap<>(16);
        //获取用户购物车信息
        Map<Object, Object> cartMap = redisUtils.hmget(String.valueOf(updateCartDto.getUid()));
        //清空缓存
        redisUtils.del(String.valueOf(updateCartDto.getUid()));
        //循环购物车信息
        cartMap.forEach((k, v) -> {
            //寻找修改的图书
            if (k.equals(String.valueOf(updateCartDto.getBid()))) {
                CartDto cartDto = (CartDto) v;
                cartDto.setNum(updateCartDto.getNum());
            }
            cartBookMap.put(k.toString(), v);
        });
        return redisUtils.hmset(updateCartDto.getUid().toString(), cartBookMap);
    }

    @Override
    public boolean deleteSignal(DeleteSignalDto deleteSignalDto) {
        Map<String, Object> cartBookMap = new HashMap<>(16);
        //获取购物车信息
        Map<Object, Object> cartMap = redisUtils.hmget(String.valueOf(deleteSignalDto.getUid()));
        //删除缓存
        redisUtils.del(String.valueOf(deleteSignalDto.getUid()));
        //循环购物车信息
        cartMap.forEach((k, v) -> {
            if (k.equals(String.valueOf(deleteSignalDto.getBid()))) {
                return;
            }
            cartBookMap.put(k.toString(), v);
        });
        return redisUtils.hmset(String.valueOf(deleteSignalDto.getUid()), cartBookMap);
    }

    @Override
    public boolean deleteMultiple(DeleteMultiple deleteMultiple) {
        Map<String, Object> cartBookMap = new HashMap<>(16);
        //获取要删除的图书编号
        List<Integer> bookIdList = deleteMultiple.getBidList();
        //获取购物车信息
        Map<Object, Object> cartMap = redisUtils.hmget(String.valueOf(deleteMultiple.getUid()));
        //删除缓存
        redisUtils.del(String.valueOf(deleteMultiple.getUid()));
        //是否删除判断器
        boolean isDelete = false;
        //循环购物车信息
        for (Map.Entry<Object, Object> entry : cartMap.entrySet()) {
            for (int bid : bookIdList) {
                if (entry.getKey().equals(String.valueOf(bid))) {
                    isDelete = true;
                    break;
                }
            }
            if (!isDelete) {
                cartBookMap.put(entry.getKey().toString(), entry.getValue());
            }
            isDelete = false;
        }
        return redisUtils.hmset(String.valueOf(deleteMultiple.getUid()), cartBookMap);
    }

    @Override
    public OrderBookDto getOrderBook(int uid) {
        //获取订单信息存储器
        Map<Integer, Object> orderBookMap = CommonBean.OrderBookMap;
        OrderBookDto orderBookDto = null;
        for (Map.Entry<Integer, Object> entry : orderBookMap.entrySet()) {
            if (entry.getKey() == uid) {
                orderBookDto = (OrderBookDto) entry.getValue();
                break;
            }
        }
        return orderBookDto;
    }
}
