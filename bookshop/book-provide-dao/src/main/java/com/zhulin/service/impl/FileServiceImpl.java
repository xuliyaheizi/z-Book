package com.zhulin.service.impl;

import com.aliyun.oss.OSSClient;
import com.zhulin.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author:ZHULIN
 * @Description:
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Value("${aliYun.oss.bucketName}")
    private String bucketName;

    @Value("${aliYun.oss.dir.prefix}")
    private String dirPrefix;

    @Resource
    private OSSClient ossClient;

    @Override
    public String upload(MultipartFile file) {
        try {
            return upload(file.getInputStream(), file.getOriginalFilename());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public String upload(InputStream inputStream, String name) {
        String objectName = getBucketName(name);
        // 创建PutObject请求。
        ossClient.putObject(bucketName, objectName, inputStream);
        return formatPath(objectName);
    }

    private String getBucketName(String url) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 获取当前时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        String dateUrl = format.format(date);
        return dirPrefix + "/" + dateUrl + "/" + uuid + url;
    }

    /**
     * 返回图片在OSS服务器内的路径
     * @param objectName 文件名
     */
    private String formatPath(String objectName) {
        return "https://" + bucketName + "." + ossClient.getEndpoint().getHost() + "/" + objectName;
    }
}
