package com.zhulin.controller;

import com.zhulin.bean.JsonModel;
import com.zhulin.constants.UrlConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author：ZHULIN
 * @Description:分类 @Create：2022/04/08
 */
@RestController
@Slf4j
public class CateGoryController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/cateGory/all")
    public JsonModel getCateGoryAll() {
        return restTemplate.getForObject(UrlConstant.BASE_URL + "/cateGory/all", JsonModel.class);
    }
}
