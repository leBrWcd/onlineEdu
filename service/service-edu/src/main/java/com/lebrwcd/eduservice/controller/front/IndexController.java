package com.lebrwcd.eduservice.controller.front;/**
 * @author lebrwcd
 * @date 2022/5/11
 * @note
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lebrwcd.commonutils.R;
import com.lebrwcd.eduservice.entity.EduCourse;
import com.lebrwcd.eduservice.entity.EduTeacher;
import com.lebrwcd.eduservice.service.EduCourseService;
import com.lebrwcd.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Query;
import java.util.List;

/**
 * ClassName IndexController
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/11
 */
@RestController
@RequestMapping("eduservice/indexfront")
public class IndexController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @Cacheable(key = "'indexList'",value = "index")
    @GetMapping("index")
    public R index() {

        //按照id降序排列，取出前4个讲师
        QueryWrapper<EduTeacher> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByDesc("id");
        wrapper1.last("limit 4");
        List<EduTeacher> eduTeachers = teacherService.list(wrapper1);


        //按照id降序排列，取出前8个课程
        QueryWrapper<EduCourse> wrapper2 = new QueryWrapper<>();
        wrapper2.orderByDesc("id");
        wrapper2.last("limit 8");
        List<EduCourse> eduCourses = courseService.list(wrapper2);

        return R.ok().data("courseList",eduCourses).data("teacherList",eduTeachers);
    }

}
