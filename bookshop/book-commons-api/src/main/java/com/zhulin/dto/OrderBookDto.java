package com.zhulin.dto;

import com.zhulin.vo.CartBookVo;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author:ZHULIN
 * @Date:2022/4/28
 * @Description: 图书订单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "存储图书订单信息")
public class OrderBookDto {
    /**
     * 用户编号
     */
    private Integer uid;
    /**
     * 总金额
     */
    private Double actualPrice;
    /**
     * 需要下单的图书列表
     */
    private List<CartBookVo> buyBookList;

}
