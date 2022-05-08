package com.zhulin.controller;

import com.zhulin.bean.JsonModel;
import com.zhulin.constants.UrlConstant;
import com.zhulin.dto.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author:ZHULIN
 * @Date:2022/4/26
 * @Description: 购物车模块
 */
@RestController
public class CartController {
    @Resource
    private RestTemplate restTemplate;

    @PostMapping("cart/add")
    public JsonModel addBookToCart(@RequestBody CartDto cartDto) {
        return restTemplate.postForObject(UrlConstant.BASE_URL + "/add/cart", cartDto, JsonModel.class);
    }

    @GetMapping("/cart/show/{uid}")
    public JsonModel showCart(@PathVariable int uid) {
        return restTemplate.getForObject(UrlConstant.BASE_URL + "/show/cart/" + uid, JsonModel.class);
    }

    @PostMapping("/cart/updateNum")
    public JsonModel updateCartBookNum(@RequestBody UpdateCartDto updateCartDto) {
        return restTemplate.postForObject(UrlConstant.BASE_URL + "/update/cart", updateCartDto, JsonModel.class);
    }

    @PostMapping("/cart/deleteSignal")
    public JsonModel deleteSignal(@RequestBody DeleteSignalDto deleteSignalDto) {
        return restTemplate.postForObject(UrlConstant.BASE_URL + "/deleteSignal/cart", deleteSignalDto,
                JsonModel.class);
    }

    @PostMapping("/cart/deleteMultiple")
    public JsonModel deleteMultiple(@RequestBody DeleteMultiple deleteMultiple) {
        return restTemplate.postForObject(UrlConstant.BASE_URL + "/deleteMultiple/cart", deleteMultiple,
                JsonModel.class);
    }

    @PostMapping("/cart/addOrderBook")
    public JsonModel addOrderBook(@RequestBody OrderBookDto orderBookDto) {
        return restTemplate.postForObject(UrlConstant.BASE_URL + "/cart/addOrderBook", orderBookDto, JsonModel.class);
    }

    @GetMapping("/cart/getOrderBook/{uid}")
    public JsonModel getOrderBook(@PathVariable int uid) {
        return restTemplate.getForObject(UrlConstant.BASE_URL + "/cart/getOrderBook/" + uid, JsonModel.class);
    }
}
