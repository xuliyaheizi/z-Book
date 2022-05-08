package com.zhulin.controller;

import com.zhulin.bean.JsonModel;
import com.zhulin.constants.UrlConstant;
import com.zhulin.dto.SignUpParam;
import com.zhulin.entities.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author:ZHULIN
 * @Description:
 */
@RestController
public class UserController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/user/getRandomId/{uName}")
    public JsonModel getRandomId(@PathVariable String uName) {
        return restTemplate.getForObject(UrlConstant.BASE_URL + "/user/getRandomId/" + uName, JsonModel.class);
    }

    @PostMapping("/user/login")
    public JsonModel login(@RequestBody User user) {
        return restTemplate.postForObject(UrlConstant.BASE_URL + "/user/login", user, JsonModel.class);
    }

    @PostMapping("/user/signUp")
    public JsonModel signUp(@RequestBody SignUpParam signUpParam) {
        return restTemplate.postForObject(UrlConstant.BASE_URL + "/user/signUp", signUpParam, JsonModel.class);
    }

}
