package com.lebrwcd.eduservice.client;

import com.lebrwcd.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author lebrwcd
 * @date 2022/5/10
 * @note
 */
@Component
@FeignClient(value = "service-vod",fallback = VodClientHystrix.class) //远程调用的微服务名称
public interface VodClient {

    //远程调用的微服务方法接口地址
    @DeleteMapping("/eduvod/video/{id}")
    public R deleteVideo(@PathVariable("id") String id);

    //远程调用service-vod中批量删除云端视频的接口方法
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoSourseIdList") List<String> videoSourseIdList);

}
