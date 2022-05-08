package com.zhulin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author:ZHULIN
 * @Description: json回传数据
 * @Date: 2022/4/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonModel<T> implements Serializable {
    /** 响应状态码 */
    private Integer code;
    /** 响应信息 */
    private String msg;
    /** 响应数据 */
    private T data;

    public JsonModel(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
