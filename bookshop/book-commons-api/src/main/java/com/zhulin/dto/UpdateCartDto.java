package com.zhulin.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:ZHULIN
 * @Date:2022/4/27
 * @Description: 修改购物车图书数量
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "修改购物车车图书数量")
public class UpdateCartDto {
    private Integer uid;
    private Integer bid;
    private Integer num;
}
