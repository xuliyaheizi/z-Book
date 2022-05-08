package com.zhulin.controller;

import com.zhulin.bean.JsonModel;
import com.zhulin.constants.UrlConstant;
import com.zhulin.dto.OrderBookDto;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author:ZHULIN
 * @Date:2022/4/30
 * @Description: 支付宝
 */
@RestController
public class AliPayController {
    @Resource
    private RestTemplate restTemplate;

    @PostMapping("/trade/page/pay")
    public JsonModel tradePagePay(@RequestBody OrderBookDto orderBookDto) {
        return restTemplate.postForObject(UrlConstant.BASE_URL + "/trade/page/pay", orderBookDto, JsonModel.class);
    }

    @PostMapping("/trade/notify")
    public String tradeNotify(@RequestParam Map<String, String> params) {
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(params);
        return restTemplate.postForObject(UrlConstant.BASE_URL + "/trade/notify", entity, String.class);
    }
}
