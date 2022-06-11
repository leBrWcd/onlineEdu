package com.lebrwcd.eduservice.mapper;

import com.lebrwcd.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lebrwcd.eduservice.entity.frontvo.CourseWebVo;
import com.lebrwcd.eduservice.entity.vo.CoursePublishVo;

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
}
