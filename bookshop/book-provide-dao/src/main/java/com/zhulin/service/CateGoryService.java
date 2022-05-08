package com.zhulin.service;

import java.util.List;
import java.util.Map;

/**
 * @author：ZHULIN
 * @Description:分类 @Create：2022/04/08
 */
public interface CateGoryService {

    /**
     * 获取分类类别
     * @return
     */
    List<Map<String, Object>> getCateGoryList();
}
