package com.zhulin.config;

import com.alipay.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:ZHULIN
 * @Date:2022/4/30
 * @Description: 支付宝支付配置文件
 */
@Configuration
public class AlipayClientConfig {
    @Value("${alipay.gateway-url}")
    String gatewayUrl;
    @Value("${alipay.app-id}")
    String appId;
    @Value("${alipay.merchant-private-key}")
    String merchantPrivateKey;
    @Value("${alipay.alipay-public-key}")
    String publicKey;

    @Bean
    public AlipayClient alipayClient() throws AlipayApiException {

        AlipayConfig alipayConfig = new AlipayConfig();

        //设置网关地址
        alipayConfig.setServerUrl(gatewayUrl);
        //设置应用Id
        alipayConfig.setAppId(appId);
        //设置应用私钥
        alipayConfig.setPrivateKey(merchantPrivateKey);
        //设置请求格式，固定值json
        alipayConfig.setFormat(AlipayConstants.FORMAT_JSON);
        //设置字符集
        alipayConfig.setCharset(AlipayConstants.CHARSET_UTF8);
        //设置支付宝公钥
        alipayConfig.setAlipayPublicKey(publicKey);
        //设置签名类型
        alipayConfig.setSignType(AlipayConstants.SIGN_TYPE_RSA2);
        //构造client

        return new DefaultAlipayClient(alipayConfig);
    }
}
