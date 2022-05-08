package com.zhulin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author:ZHULIN
 * @Date:2022/4/28
 * @Description: 删除多个图书
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteMultiple {
    private Integer uid;
    private List<Integer> bidList;
}
