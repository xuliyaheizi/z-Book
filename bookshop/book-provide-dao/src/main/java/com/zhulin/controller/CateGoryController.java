package com.zhulin.controller;

import com.zhulin.bean.JsonModel;
import com.zhulin.service.CateGoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZHULIN
 * @Description: 分类
 * @Date: 2022/04/08
 */
@RestController
@Api(tags = "分类模块")
public class CateGoryController {
    @Resource
    private CateGoryService cateGoryService;

    @GetMapping("/provide/cateGory/all")
    @ApiOperation("获取所有分类")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "查找成功"),
            @ApiResponse(code = 444, message = "查找失败")
    })
    public JsonModel<List<Map<String, Object>>> getCateGoryAll() {
        List<Map<String, Object>> category = cateGoryService.getCateGoryList();
        if (category != null && category.size() > 0) {
            return new JsonModel<>(200, "查找成功", category);
        }
        return new JsonModel<>(444, "查找失败");
    }
}
