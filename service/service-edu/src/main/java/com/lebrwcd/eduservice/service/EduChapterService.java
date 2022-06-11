package com.lebrwcd.eduservice.service;

import com.lebrwcd.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lebrwcd.eduservice.entity.course.Chapter;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-04
 */
public interface EduChapterService extends IService<EduChapter> {

    List<Chapter> getChapeters(String courseId);

    boolean deleteById(String chapterId);

    void deleteByCourseId(String courseId);
}
