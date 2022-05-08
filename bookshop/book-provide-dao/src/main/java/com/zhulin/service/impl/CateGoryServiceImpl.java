package com.zhulin.service.impl;

import com.zhulin.dao.CateGoryDao;
import com.zhulin.entities.CateGory;
import com.zhulin.service.CateGoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZHULIN
 * @Description:
 * @Date: 2022/04/08
 */
@Service
public class CateGoryServiceImpl implements CateGoryService {
    @Resource
    private CateGoryDao cateGoryDao;

    @Override
    public List<Map<String, Object>> getCateGoryList() {
        // 分类集合
        List<Map<String, Object>> cateGorys = new ArrayList<>();
        // 获取所有一级分类
        List<CateGory> cateGoryList = cateGoryDao.getCateGoryStair();
        // 循环一级分类 获取该一级分类的二级分类
        cateGoryList.forEach(cg -> {
            Map<String, Object> cateGory = new HashMap<>(16);
            List<CateGory> cateGoryListSecond = cateGoryDao.getCateGorySecondById(cg.getCid());
            cateGory.put("CateGory", cg);
            cateGory.put("cateGoryListSecond", cateGoryListSecond);
            cateGorys.add(cateGory);
        });
        return cateGorys;
    }
}
