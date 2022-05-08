package com.zhulin.controller;

import com.zhulin.constants.UrlConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author:ZHULIN
 * @Date: 2022/4/19
 * @Description: CSDN热榜
 */
@RestController
@RequestMapping("/provide")
@Api(tags = "CSDN热榜模块")
public class NewsController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/getCsdns")
    @ApiOperation("获取CSDN热榜信息")
    public ResponseEntity<String> getNews() {
        // 添加请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("access-key", "e5716b43ad923982f410b0fa08596c62");
        headers.add("secret-key", "7fb9e23dd9b4841ece9e4d4ca1920c85");
        // 创建请求实体
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        return restTemplate.exchange(UrlConstant.CSDN_URL, HttpMethod.GET, httpEntity, String.class);
    }
}
