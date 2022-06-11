package com.lebrwcd.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.commonutils.R;
import com.lebrwcd.commonutils.vo.CourseVo;
import com.lebrwcd.eduservice.entity.EduCourse;
import com.lebrwcd.eduservice.entity.EduTeacher;
import com.lebrwcd.eduservice.entity.vo.CourseInfo;
import com.lebrwcd.eduservice.entity.vo.CoursePublishVo;
import com.lebrwcd.eduservice.entity.vo.CourseQuery;
import com.lebrwcd.eduservice.entity.vo.TeacherQuery;
import com.lebrwcd.eduservice.service.EduCourseService;
import com.lebrwcd.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-04
 */
@RestController
@RequestMapping("/eduservice/course")
@Slf4j
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 根据课程id删除课程，需要先删除 章节，小节，课程描述，课程本身
     * @param courseId
     * @return
     * @TODO 完善：删除课程时候，每个章节下有多个小节，每个小节有一个视频，批量删除视频
     */
    @DeleteMapping("{courseId}")
    public R deleteCourseById(@PathVariable String courseId) {

        courseService.deleteCourse(courseId);

        return R.ok();
    }


    /**
     *
     * @param current  当前页码
     * @param limit    每页显示多少条记录
     * @return
     */
    @ApiOperation(value = "分页查询")
    @PostMapping("page/{current}/{limit}")
    public R pageList(@PathVariable("current") long current,
                      @PathVariable("limit") long limit,
                      @RequestBody(required = false) CourseQuery courseQuery){

        //分页查询
        Page<EduCourse> coursePage = new Page<>(current,limit);

        //根据条件查询
        //page方法返回的分页数据封装在 分页对象eduTeacherPage中
        courseService.pageQuery(coursePage,courseQuery);

        log.info("共" + coursePage.getTotal() +"条记录");

        //总记录数
        long total = coursePage.getTotal();
        //每页显示的记录
        List<EduCourse> records = coursePage.getRecords();

        //返回数据
        return R.ok().data("total",total).data("raws",records);

    }


    /**
     * 查询所有课程
     * @return
     */
    @GetMapping
    public R getCourseList() {

        List<EduCourse> list = courseService.list(null);
        log.info("共有 " + list.size() +"条记录");
        return R.ok().data("list",list);
    }

    /**
     * 课程最终发布
     * @param courseId
     * @return
     */
    @PostMapping("publish/{courseId}")
    public R publish(@PathVariable String courseId) {

        courseService.publish(courseId);

        return R.ok();
    }

    /**
     * 课程发布，获得课程信息-讲师-章节
     * @param courseId
     * @return
     */
    @GetMapping("publishCourseInfo/{courseId}")
    public R publishCourse(@PathVariable String courseId) {

        CoursePublishVo coursePublishVo = courseService.publishCourse(courseId);

        return R.ok().data("publishCourse",coursePublishVo);

    }

    /**
     * 添加课程基本 信息
     * @param courseInfo
     * @return
     */
    @PostMapping("addCourse")
    public R addCourse(@RequestBody CourseInfo courseInfo){

        //添加课程基本信息
        String cid = courseService.addCourseInfo(courseInfo);

        return R.ok().data("courseId",cid);
    }


    /**
     * 根据课程id获得课程基本信息
     * @param courseId
     * @return
     */
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){

        CourseInfo courseInfo = courseService.getCourseInfoById(courseId);

        return R.ok().data("courseInfo",courseInfo);

    }

    /**
     * 更新课程基本信息，涉及两张表
     * @param courseInfo
     * @return
     */
    @PostMapping("update")
    public R update(@RequestBody CourseInfo courseInfo){

        courseService.updateCourseInfo(courseInfo);
        return R.ok();
    }



    /**
     * 远程方法调用：根据课程id获得课程基本信息
     * @param courseId
     * @return
     */
    @GetMapping("RemotegetCourse/{courseId}")
    public CourseVo RemoteGetCourse(@PathVariable String courseId){

        CourseInfo courseInfo = courseService.getCourseInfoById(courseId);

        String teacherId = courseInfo.getTeacherId();

        EduTeacher teacher = teacherService.getById(teacherId);
        String teacherName = teacher.getName();


        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(courseInfo,courseVo);

        courseVo.setTeacherName(teacherName);

        return courseVo;

    }


}

