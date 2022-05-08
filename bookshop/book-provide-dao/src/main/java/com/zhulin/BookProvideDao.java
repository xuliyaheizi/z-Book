package com.zhulin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author:ZHULIN
 * @Date: 2022/4/5
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.zhulin.mapper")
public class BookProvideDao {
    public static void main(String[] args) {
        SpringApplication.run(BookProvideDao.class, args);
    }
}
