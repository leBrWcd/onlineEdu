package com.lebrwcd.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author lebrwcd
 * @date 2022/5/2
 * @note
 */
public interface OssService {

    /**
     * 文件上传到阿里云oss
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
