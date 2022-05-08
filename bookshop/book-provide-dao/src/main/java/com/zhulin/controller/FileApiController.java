package com.zhulin.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zhulin.bean.JsonModel;
import com.zhulin.service.FileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * @Author:ZHULIN
 * @Description:图片上传
 */
@RestController
@Api(tags = "文件模块")
@RequestMapping("/provide")
public class FileApiController {
    @Resource
    private FileService fileService;

    @PostMapping("/file/upload")
    @ApiOperation("上传文件")
    @ApiImplicitParam(name = "file", value = "文件信息", required = true, dataType = "MultipartFile")
    @ApiResponse(code = 200, message = "上传成功")
    public JsonModel fileUpload(@RequestParam("file") MultipartFile file) {
        // 获取上传文件
        String url = fileService.upload(file);
        return new JsonModel(200, "上传成功", url);
    }
}
