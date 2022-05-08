package com.zhulin.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author：ZHULIN
 * @Description:分类
 * @Create：2022/04/08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("b_category")
public class CateGory implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer cid;
    private String cname;
    private Integer cparentid;
    private Integer cdepth;
    private Integer cstatus;
}
