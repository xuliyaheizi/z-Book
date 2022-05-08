package com.zhulin.controller;

import com.zhulin.constants.UrlConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author:ZHULIN
 * @Date: 2022/4/18
 * @Description: 全国城市列表
 */
@RestController
@RequestMapping("/provide")
@Api(tags = "全国城市模块")
public class SiteController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/site")
    @ApiOperation("获取全国城市列表")
    public String getAllSite() {
        return restTemplate.getForObject(UrlConstant.SITE_URL, String.class);
    }
}
