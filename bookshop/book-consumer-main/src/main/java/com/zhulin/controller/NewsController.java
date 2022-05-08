package com.zhulin.controller;

import com.zhulin.constants.UrlConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Program:bookshop
 * @author：ZHULIN
 * @Description:获取通知 @Create：2022/4/6
 */
@RestController
@Slf4j
public class NewsController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/news/getCsdns")
    public String getNews() {
        return restTemplate.getForObject(UrlConstant.BASE_URL + "/getCsdns", String.class);
    }
}
