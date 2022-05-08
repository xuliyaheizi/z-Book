package com.zhulin.dto;

import com.zhulin.entities.Address;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:ZHULIN
 * @Date: 2022/4/19
 * @Description: 添加收获地址参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "收获地址参数")
public class AddressDto {
    /** 地址信息 */
    private Address address;
    /** 用户Id */
    private Integer uid;
}
