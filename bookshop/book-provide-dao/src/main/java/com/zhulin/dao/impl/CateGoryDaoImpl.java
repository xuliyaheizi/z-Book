package com.zhulin.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhulin.dao.CateGoryDao;
import com.zhulin.entities.CateGory;
import com.zhulin.mapper.CateGoryMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:ZHULIN
 * @Description:
 * @Date: 2022/04/08
 */
@Repository
public class CateGoryDaoImpl implements CateGoryDao {
    @Resource
    private CateGoryMapper cateGoryMapper;

    @Override
    public List<CateGory> getCateGoryStair() {
        return cateGoryMapper.selectList(new QueryWrapper<CateGory>().eq("cdepth", 1));
    }

    @Override
    public List<CateGory> getCateGorySecondById(Integer id) {
        return cateGoryMapper.selectList(new QueryWrapper<CateGory>().eq("cparentid", id));
    }
}
