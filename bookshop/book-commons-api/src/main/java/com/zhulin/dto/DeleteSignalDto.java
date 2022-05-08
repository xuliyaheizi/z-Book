package com.zhulin.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:ZHULIN
 * @Date:2022/4/27
 * @Description: 删除单个图书
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "删除单个图书")
public class DeleteSignalDto {
    private Integer uid;
    private Integer bid;
}
