package com.zhulin.controller;

import com.zhulin.commons.CommonBean;
import com.zhulin.constants.ComConstants;
import com.zhulin.constants.UrlConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author:ZHULIN
 * @Date: 2022/4/19
 * @Description: 验证码发送模块
 */
@RestController
@RequestMapping("/provide")
@Api(tags = "验证码模块")
public class CaptchaController {
    @Resource
    private RestTemplate restTemplate;

    @PostMapping("/getCaptcha/{mobile}")
    @ApiOperation("获取验证码")
    @ApiImplicitParam(name = "mobile", value = "手机号码", required = true)
    public ResponseEntity<String> getCaptcha(@PathVariable String mobile) {
        // 随机生成五位数字 Math.random()是令系统随机选取大于等于 0.0 且小于 1.0 的伪随机 double 值
        int code = (int) (Math.random() * 50000 + 20000);
        CommonBean.CodeMap.put("code", code);
        // 添加请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "APPCODE " + ComConstants.APPCODE);
        HttpEntity entity = new HttpEntity<>(null, headers);
        //验证码地址
        String captChaUrl = String.format(UrlConstant.CAPTCHA_URL, code, mobile);

        return restTemplate.exchange(captChaUrl, HttpMethod.POST, entity, String.class);
    }
}
