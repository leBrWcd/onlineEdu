package com.lebrwcd.vod.service.impl;/**
 * @author lebrwcd
 * @date 2022/5/9
 * @note
 */

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.lebrwcd.serviceBase.exceptionhandler.GuliException;
import com.lebrwcd.vod.service.VideoService;
import com.lebrwcd.vod.utils.ConstantYamlUtil;
import com.lebrwcd.vod.utils.VodUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * ClassName VideoServiceImpl
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/9
 */
@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            //文件输入流
            InputStream inputStream = file.getInputStream();
            //文件名称
            String originalFilename = file.getOriginalFilename();
            //文件展示名称
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(ConstantYamlUtil.keyId, ConstantYamlUtil.keySecret,title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                log.warn(errorMessage);
                if(StringUtils.isEmpty(videoId)){
                    throw new GuliException(20001, errorMessage);
                }
            }

            return videoId;
        } catch (IOException e) {
            throw new GuliException(20001, "guli vod 服务上传失败");
        }
    }

    /**
     * 删除视频
     * @param id
     */
    @Override
    public void deleteVideoById(String id) {

        try {
            //初始化客户端
            DefaultAcsClient client = VodUtil.initVodClient(ConstantYamlUtil.keyId, ConstantYamlUtil.keySecret);
            //创建删除的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);

            client.getAcsResponse(request);

        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    //根据多个视频id删除云端视频
    @Override
    public void deleteBatchVideo(List<String> videoSourseIdList) {
        try {
            //初始化客户端
            DefaultAcsClient client = VodUtil.initVodClient(ConstantYamlUtil.keyId, ConstantYamlUtil.keySecret);
            //创建删除的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //将list转化为 11,22,33这种形式的字符串
            String ids = org.apache.commons.lang.StringUtils.join(videoSourseIdList.toArray(), ",");

            //传递多个id ，11,22,33 这种形式
            request.setVideoIds(ids);

            client.getAcsResponse(request);

        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }
}
