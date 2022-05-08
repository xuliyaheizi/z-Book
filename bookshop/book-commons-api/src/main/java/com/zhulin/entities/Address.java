package com.zhulin.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author:ZHULIN
 * @Date:2022/4/19
 * @Description: 收获地址
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "b_address")
@ApiModel(description = "收获地址")
public class Address implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer aid;
    private Integer uid;
    private String aname;
    private String aphone;
    private String aprovince;
    private String acity;
    private String adistrict;
    private String aplace;
    private String acreatetime;
    private Integer astate;
}
