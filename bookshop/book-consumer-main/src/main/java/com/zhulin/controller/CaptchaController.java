package com.zhulin.controller;

import com.zhulin.constants.UrlConstant;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author:ZHULIN
 * @Description:验证码发送模块
 */
@RestController
public class CaptchaController {
    @Resource
    private RestTemplate restTemplate;

    @PostMapping("/code/getCaptcha/{mobile}")
    public String getCaptcha(@PathVariable String mobile) {
        return restTemplate.postForObject(UrlConstant.BASE_URL + "/getCaptcha/" + mobile, null, String.class);
    }
}
