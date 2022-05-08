package com.zhulin.vo;

import com.zhulin.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:ZHULIN
 * @Date:2022/4/27
 * @Description: 购物车图书信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartBookVo {
    private Book book;
    private Integer bookNumber;
    private Double itemPriceId;
}
