package com.lebrwcd.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lebrwcd.eduservice.entity.EduChapter;
import com.lebrwcd.eduservice.entity.EduVideo;
import com.lebrwcd.eduservice.entity.course.Chapter;
import com.lebrwcd.eduservice.entity.course.Video;
import com.lebrwcd.eduservice.mapper.EduChapterMapper;
import com.lebrwcd.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.eduservice.service.EduVideoService;
import com.lebrwcd.serviceBase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-04
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    //为了得到所有小节
    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 获取所有章节，每个章节下面包含n个小节
     * @param courseId
     * @return
     */
    @Override
    public List<Chapter> getChapeters(String courseId) {

        //1.定义最终返回的数据
        List<Chapter> data = new ArrayList<>();

        //2.根据课程id得到所有章节
        QueryWrapper<EduChapter> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(queryWrapper1);

        //3.根据课程id得到所有小姐
        QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id",courseId);
        List<EduVideo> eduVideos = eduVideoService.list(queryWrapper2);

        //4.封装所有章节
        //遍历所有章节
        for (int i = 0; i < eduChapters.size(); i++) {
            //得到每个章节
            EduChapter eduChapter = eduChapters.get(i);
            //将章节转换为前端需要的vo格式
            Chapter chapter = new Chapter();
            BeanUtils.copyProperties(eduChapter,chapter);

            //将数据保存到最终返回
            data.add(chapter);


            //5.在对应章节下面封装小节
            List<Video> videoList = new ArrayList<>();

            for(int m = 0;m < eduVideos.size();m++){

                //得到每个小节
                EduVideo eduVideo = eduVideos.get(m);

                //判断每个小节对应的章节id是否与当前循环的章节一致
                if(eduVideo.getChapterId().equals(eduChapter.getId())){

                    Video video = new Video();
                    BeanUtils.copyProperties(eduVideo,video);
                    //将对应的小节添加到集合中
                    videoList.add(video);
                }
            }
            //将每个章节对应的小节存入章节中
            chapter.setChildren(videoList);
        }
        //6.返回最终封装完的数据
        return data;
    }

    //删除
    @Override
    public boolean deleteById(String chapterId) {

        //先判断章节下面是否有小节，如果有小节，不能进行删除
        //判断是否有小节，edu_video数据库中是否有章节id
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);

        int count = eduVideoService.count(wrapper);
        if(count > 0){
            //有小节，不能删除
            throw new GuliException(20001,"有小节，无法删除");
        }

        //可以删除,调用mapper删除该章节
        int delete = baseMapper.deleteById(chapterId);

        return delete > 0;
    }

    @Override
    public void deleteByCourseId(String courseId) {

        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();

        wrapper.eq("course_id",courseId);

        baseMapper.delete(wrapper);

    }
}
