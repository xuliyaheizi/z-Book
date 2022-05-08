package com.zhulin.config;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:ZHULIN
 * @Description: OSS服务器配置
 */
@Configuration
public class OssClientConfig {
    @Value("${aliYun.oss.endpoint}")
    String endpoint;
    @Value("${aliYun.oss.accessKeyId}")
    String accessKeyId;
    @Value("${aliYun.oss.accessKeySecret}")
    String accessKeySecret;

    @Bean
    public OSSClient createOssClient() {
        return (OSSClient) new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
