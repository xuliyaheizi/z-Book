package com.zhulin.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:ZHULIN
 * @Date:2022/4/30
 * @Description: 订单
 */
@TableName("b_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    @TableId(type = IdType.AUTO)
    private Integer oid;
    private String ono;
    private Integer uid;
    private String otitle;
    private Double ototalprice;
    private String ocodeurl;
    private String ostatus;
    private String opaytype;
    private String ocreatetime;
}
