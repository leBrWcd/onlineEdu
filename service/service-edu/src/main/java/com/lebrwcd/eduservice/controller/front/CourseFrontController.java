package com.lebrwcd.eduservice.controller.front;/**
 * @author lebrwcd
 * @date 2022/5/22
 * @note
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.commonutils.JwtUtils;
import com.lebrwcd.commonutils.R;
import com.lebrwcd.eduservice.client.OrderClient;
import com.lebrwcd.eduservice.entity.EduCourse;
import com.lebrwcd.eduservice.entity.course.Chapter;
import com.lebrwcd.eduservice.entity.frontvo.CourseFrontVo;
import com.lebrwcd.eduservice.entity.frontvo.CourseWebVo;
import com.lebrwcd.eduservice.service.EduChapterService;
import com.lebrwcd.eduservice.service.EduCourseService;
import com.lebrwcd.eduservice.service.EduVideoService;
import com.lebrwcd.serviceBase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ClassName CourseFrontController
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/22
 */
@RestController
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {


    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    //分页查询带条件获取课程列表
    @PostMapping("courseList/{pageSize}/{limit}")
    public R getAllAcourse(@PathVariable long pageSize,
                           @PathVariable long limit,
                           @RequestBody CourseFrontVo courseFrontVo) {


        //条件查询带分页
        Page<EduCourse> coursePage = new Page<>(pageSize,limit);
        Map<String,Object> map = courseService.getCoursePageList(coursePage,courseFrontVo);

        return R.ok().data(map);
    }


    //课程详情：根据路径中课程id获取课程基本信息和课程章节信息，课程所属讲师
    @GetMapping("base/{courseId}")
    public R getBaseInfo(@PathVariable String courseId, HttpServletRequest request) {

        CourseWebVo courseWebVo = courseService.getBaseInfoById(courseId);

        //远程调用查看课程是否被所属用户购买
        String idByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        if(idByJwtToken == null) {
            throw new GuliException(28004,"请登录");
        }

        boolean isbuyCourse = orderClient.isbuyCourse(courseId, idByJwtToken);

        //获取课程分类信息
        List<Chapter> chapterList = chapterService.getChapeters(courseId);

        return R.ok().data("isbuyCourse",isbuyCourse).data("courseWebVo",courseWebVo).data("chapterList",chapterList);


    }
}
