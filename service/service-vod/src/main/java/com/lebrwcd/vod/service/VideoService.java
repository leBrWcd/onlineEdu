package com.lebrwcd.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lebrwcd
 * @date 2022/5/9
 * @note
 */
public interface VideoService {
    String uploadVideo(MultipartFile file);

    void deleteVideoById(String id);

    void deleteBatchVideo(List<String> videoSourseIdList);
}
