package com.lebrwcd.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lebrwcd.commonutils.R;
import com.lebrwcd.eduservice.client.VodClient;
import com.lebrwcd.eduservice.entity.EduVideo;
import com.lebrwcd.eduservice.mapper.EduVideoMapper;
import com.lebrwcd.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.serviceBase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-04
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void deleteByCourseId(String courseId) {

        //删除课程下面的小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);

        //查询每个小节对应的视频id
        //只查询video_source_id这个字段
        wrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapper);

        //将List<EduVideo> 转化为 List<String>
        List<String> videoIds = new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {

            String videoSourceId = eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)) {
                videoIds.add(videoSourceId);
            }
        }

        //调用service-vod中的方法
        if(videoIds.size() > 0) {
            R result = vodClient.deleteBatch(videoIds);
            if(result.getCode() == 20001) {
                throw new GuliException(20002,"执行熔断机制...");
            }
        }


        //删除小节下面对应的视频，再删除小节
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
