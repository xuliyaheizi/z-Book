package com.zhulin.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:ZHULIN
 * @Date:2022/4/26
 * @Description: 购物车
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "购物车")
public class CartDto {
    /**
     * 用户id 作为主键
     */
    private Integer uid;
    /**
     * 书籍id
     */
    private Integer bid;
    /**
     * 书籍在购物车的数量
     */
    private Integer num;
}
