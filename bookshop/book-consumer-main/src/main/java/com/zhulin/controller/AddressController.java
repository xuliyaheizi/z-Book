package com.zhulin.controller;

import com.zhulin.bean.JsonModel;
import com.zhulin.constants.UrlConstant;
import com.zhulin.dto.AddressDto;
import com.zhulin.entities.Address;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:ZHULIN
 * @Date:2022/4/19
 * @Description: 收获地址
 */
@RestController
public class AddressController {
    @Resource
    private RestTemplate restTemplate;

    @PostMapping("/address/add")
    public JsonModel addAddress(@RequestBody AddressDto addressDto) {
        return restTemplate.postForObject(UrlConstant.BASE_URL + "/addAddress", addressDto, JsonModel.class);
    }

    @GetMapping("/address/show/{uid}")
    public JsonModel<List<Address>> showAddress(@PathVariable int uid) {
        return restTemplate.getForObject(UrlConstant.BASE_URL + "/showAddress/" + uid, JsonModel.class);
    }

    @PostMapping("/address/delete/{aid}")
    public JsonModel deleteAddress(@PathVariable Integer aid) {
        return restTemplate.postForObject(UrlConstant.BASE_URL + "/deleteAddress/" + aid, null, JsonModel.class);
    }

    @GetMapping("/address/default/{uid}")
    public JsonModel getDefaultAddress(@PathVariable int uid) {
        return restTemplate.getForObject(UrlConstant.BASE_URL + "/get/default/address/" + uid, JsonModel.class);
    }
}
