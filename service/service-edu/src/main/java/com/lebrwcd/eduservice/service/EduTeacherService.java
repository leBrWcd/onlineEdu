package com.lebrwcd.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lebrwcd.eduservice.entity.vo.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-04-24
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //自定义的分页条件查询
    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

    Map<String, Object> pageTeacher(Page<EduTeacher> teacherPage);
}
