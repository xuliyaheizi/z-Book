package com.zhulin.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @Author:ZHULIN
 * @Description: 文件上传
 */
public interface FileService {
    /**
     * 图片上传
     * @param file
     * @return
     */
    String upload(MultipartFile file);

    /**
     * 上传
     * @param inputStream
     * @param name
     * @return
     */
    String upload(InputStream inputStream, String name);
}
