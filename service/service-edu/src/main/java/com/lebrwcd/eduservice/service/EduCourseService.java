package com.lebrwcd.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lebrwcd.eduservice.entity.frontvo.CourseFrontVo;
import com.lebrwcd.eduservice.entity.frontvo.CourseWebVo;
import com.lebrwcd.eduservice.entity.vo.CourseInfo;
import com.lebrwcd.eduservice.entity.vo.CoursePublishVo;
import com.lebrwcd.eduservice.entity.vo.CourseQuery;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-04
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程基本信息
    String addCourseInfo(CourseInfo courseInfo);

    CourseInfo getCourseInfoById(String courseId);

    void updateCourseInfo(CourseInfo courseInfo);

    CoursePublishVo publishCourse(String courseId);

    void publish(String courseId);

    void pageQuery(Page<EduCourse> coursePage, CourseQuery courseQuery);

    void deleteCourse(String courseId);

    Map<String, Object> getCoursePageList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseInfoById(String courseId);
}
