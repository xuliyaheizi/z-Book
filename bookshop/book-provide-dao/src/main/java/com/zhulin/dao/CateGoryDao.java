package com.zhulin.dao;

import com.zhulin.entities.CateGory;

import java.util.List;

/**
 * @author：ZHULIN
 * @Description: @Create：2022/04/08
 */
public interface CateGoryDao {

    /**
     * 获取一级分类
     * @return
     */
    List<CateGory> getCateGoryStair();

    /**
     * 根据一级分类的Id获取二级分类
     * @param id
     * @return
     */
    List<CateGory> getCateGorySecondById(Integer id);
}
