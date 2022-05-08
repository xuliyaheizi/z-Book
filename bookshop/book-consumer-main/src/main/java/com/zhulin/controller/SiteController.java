package com.zhulin.controller;

import com.zhulin.constants.UrlConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author:ZHULIN
 * @Date:2022/4/18
 * @Description:
 */
@RestController
public class SiteController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/site")
    public String getAllSite() {
        return restTemplate.getForObject(UrlConstant.BASE_URL + "/site", String.class);
    }
}
