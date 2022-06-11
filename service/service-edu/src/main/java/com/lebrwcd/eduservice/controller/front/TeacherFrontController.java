package com.lebrwcd.eduservice.controller.front;/**
 * @author lebrwcd
 * @date 2022/5/21
 * @note
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.commonutils.R;
import com.lebrwcd.eduservice.entity.EduCourse;
import com.lebrwcd.eduservice.entity.EduTeacher;
import com.lebrwcd.eduservice.service.EduCourseService;
import com.lebrwcd.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ClassName TeacherFrontController
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/21
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    /**
     * 讲师分页
     */
    @GetMapping("/page/{pageSize}/{limit}")
    public R page(@PathVariable long pageSize, @PathVariable long limit) {

        //分页对象
        Page<EduTeacher> teacherPage = new Page<>(pageSize, limit);

        Map<String, Object> map = teacherService.pageTeacher(teacherPage);

        return R.ok().data(map);
    }

    /**
     * 讲师详情接口
     */
    @GetMapping("detail/{teacherid}")
    public R teacherDetail(@PathVariable String teacherid) {

        //根据讲师id查询讲师信息
        EduTeacher eduTeacher = teacherService.getById(teacherid);


        //根据讲师id查询讲师所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherid);
        List<EduCourse> courseList = courseService.list(wrapper);

        return R.ok().data("courseList",courseList).data("teacher",eduTeacher);
    }

}
