package com.lebrwcd.eduservice.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lebrwcd.eduservice.entity.frontvo.CourseFrontVo;
import com.lebrwcd.eduservice.entity.frontvo.CourseWebVo;
import com.lebrwcd.eduservice.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-04
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //查询课程发布需要显示的内容
    CoursePublishVo getCoursePublishVoById(String courseId);

    CourseWebVo getBaseInfoById(String courseId);

    // 根据课程id获得课程评论数量
    Long getCommentById(@Param("courseId") String id);
}
