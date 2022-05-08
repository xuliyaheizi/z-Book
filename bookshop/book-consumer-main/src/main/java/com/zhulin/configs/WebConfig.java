package com.zhulin.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Program:bookshop
 * @author：ZHULIN
 * @Description: @Create：2022/4/6
 */
@Configuration
public class WebConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
