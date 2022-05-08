package com.zhulin.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @Author:ZHULIN
 * @Date: 2022/5/5
 * @Description: Druid配置类
 */
@Configuration
public class DruidConfig implements WebMvcConfigurer {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    //后台监控
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean<StatViewServlet> statViewServlet =
                new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        //配置后台登录用户名密码
        HashMap<String, String> objectObjectHashMap = new HashMap<>(16);
        //用户名参数密码不能改变，系统配置
        objectObjectHashMap.put("loginUsername", "admin");
        objectObjectHashMap.put("loginPassword", "admin");
        //允许谁可以访问  为空时所有人可以访问 例如：objectObjectHashMap.put("allow","localhost"); 代表只能自己访问
        objectObjectHashMap.put("allow", "");

        statViewServlet.setInitParameters(objectObjectHashMap);
        return statViewServlet;
    }
}
