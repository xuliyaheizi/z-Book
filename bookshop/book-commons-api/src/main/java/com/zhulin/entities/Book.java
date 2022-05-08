package com.zhulin.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @Program:bookshop
 * @author：ZHULIN
 * @Description:书本类 @Create：2022/4/5
 */
@TableName(value = "b_book")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "书本")
public class Book implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer bid;
    private String bname;
    private String bauthor;
    private String bpress;
    private String babstract;
    private String bdescription;
    private Double bprice;
    private Integer brepertory;
    private String bclasses;
    private String bimg;
    private BigInteger bisbn;
    private String bpublicationdate;
    private Integer bstate;
}
