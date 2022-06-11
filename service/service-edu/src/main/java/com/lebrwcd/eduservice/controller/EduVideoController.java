package com.lebrwcd.eduservice.controller;


import com.lebrwcd.commonutils.R;
import com.lebrwcd.eduservice.client.VodClient;
import com.lebrwcd.eduservice.entity.EduVideo;
import com.lebrwcd.eduservice.service.EduVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-04
 */
@RestController
@RequestMapping("/eduservice/video")
@Slf4j
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    /**
     * 添加小节
     * @param eduVideo
     * @return
     */
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){

        log.info("eduvideo：" + eduVideo.toString());

        boolean save = eduVideoService.save(eduVideo);


        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //修改小节
    @PostMapping("update")
    public R updateVideo(@RequestBody EduVideo eduVideo){

        boolean update = eduVideoService.updateById(eduVideo);

        if(update){
            return R.ok();
        }else{
            return R.error();
        }

    }




    //根据小节id查询小节
    @GetMapping("getVideo/{videoId}")
    public R getVideo(@PathVariable String videoId){

        EduVideo video = eduVideoService.getById(videoId);

        return R.ok().data("video",video);

    }

    //删除小节
    @DeleteMapping("{videoId}")
    public R deleteVideo(@PathVariable String videoId){

        //根据小节id获取小节中对应的视频id
        EduVideo eduVideo = eduVideoService.getById(videoId);

        String videoSourceId = eduVideo.getVideoSourceId();

        log.info("sourceId:" + videoSourceId);

        //远程调用
        if(!StringUtils.isEmpty(videoSourceId)) {
            vodClient.deleteVideo(videoSourceId);
        }

        //删完视频后再删除小节
        boolean remove = eduVideoService.removeById(videoId);

        if(remove){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

