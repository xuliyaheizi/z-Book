package com.zhulin.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:ZHULIN
 * @Date:2022/4/24
 * @Description: 分页参数
 */
@Data
@ApiModel(description = "分页参数")
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {
    private Integer pageIndex;
    private Integer pageSize;
}
