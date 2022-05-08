package com.zhulin.dto;

import com.zhulin.entities.User;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:ZHULIN
 * @Description:注册用户参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("注册用户信息")
public class SignUpParam {
    private User user;
    private Integer code;
}
