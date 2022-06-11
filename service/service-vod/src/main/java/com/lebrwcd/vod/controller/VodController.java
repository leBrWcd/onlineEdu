package com.lebrwcd.vod.controller;/**
 * @author lebrwcd
 * @date 2022/5/9
 * @note
 */

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.lebrwcd.commonutils.R;
import com.lebrwcd.serviceBase.exceptionhandler.GuliException;
import com.lebrwcd.vod.service.VideoService;
import com.lebrwcd.vod.utils.ConstantYamlUtil;
import com.lebrwcd.vod.utils.VodUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.POST;
import java.util.List;

/**
 * ClassName VodController
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/9
 */
@RestController
@RequestMapping("eduvod/video")
@Slf4j
public class VodController {

    @Autowired
    private VideoService videoService;


    /**
     * 上传视频到云端
     *
     * @param file
     * @return
     */
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file) {

        String videoId = videoService.uploadVideo(file);

        return R.ok().data("videoId", videoId);

    }

    /**
     * 根据视频id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable("id") String id) {

        log.info("删除视频的id为：" + id);

        videoService.deleteVideoById(id);

        System.out.println("删除id为：" + id);

        return R.ok();
    }

    /**
     * 批量删除小节视频
     *
     * @param videoSourseIdList
     * @return
     */
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoSourseIdList") List<String> videoSourseIdList) {

        videoService.deleteBatchVideo(videoSourseIdList);

        return R.ok();

    }

    /**
     * 根据视频id获取视频播放凭证
     * @param id
     * @return
     */
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {


        //创建初始化对象
        DefaultAcsClient client = VodUtil.initVodClient(ConstantYamlUtil.keyId,ConstantYamlUtil.keySecret);

        //创建获取凭证request和resonse对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

        //向request设置视频id
        request.setVideoId(id);

        //得到凭证
        GetVideoPlayAuthResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        String playAuth = response.getPlayAuth();

        return R.ok().data("playAuth",playAuth);
    }

}
