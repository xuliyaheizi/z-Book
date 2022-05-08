package com.zhulin.dto;

import com.zhulin.entities.Book;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author:ZHULIN
 * @Description:图书信息修改参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "图书信息修改参数")
public class BookParams implements Serializable {
    private Book book;
    private Integer bid;
}
