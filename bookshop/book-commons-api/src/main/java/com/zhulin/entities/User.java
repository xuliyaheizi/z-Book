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
 * @Description:用户类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("b_user")
@ApiModel("用户信息")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer uid;
    private String uname;
    private String upassword;
    private String uphone;
    private String usex;
    private Integer uage;
    private Integer ustatus;
}
