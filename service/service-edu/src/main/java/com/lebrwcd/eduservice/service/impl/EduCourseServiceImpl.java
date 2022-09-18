package com.lebrwcd.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.eduservice.entity.EduCourse;
import com.lebrwcd.eduservice.entity.EduCourseDescription;
import com.lebrwcd.eduservice.entity.EduTeacher;
import com.lebrwcd.eduservice.entity.frontvo.CourseFrontVo;
import com.lebrwcd.eduservice.entity.frontvo.CourseWebVo;
import com.lebrwcd.eduservice.entity.vo.CourseInfo;
import com.lebrwcd.eduservice.entity.vo.CoursePublishVo;
import com.lebrwcd.eduservice.entity.vo.CourseQuery;
import com.lebrwcd.eduservice.mapper.EduCourseMapper;
import com.lebrwcd.eduservice.service.EduChapterService;
import com.lebrwcd.eduservice.service.EduCourseDescriptionService;
import com.lebrwcd.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.eduservice.service.EduVideoService;
import com.lebrwcd.serviceBase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-04
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    @Autowired
    private EduCourseDescriptionService descriptionService;

    @Resource
    private EduCourseMapper eduCourseMapper;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;


    /**
     * 前台页面展示课程信息
     * @param coursePage     分页对象
     * @param courseFrontVo  查询条件
     * @return
     */
    @Override
    public Map<String, Object> getCoursePageList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo) {

        //定义查询条件wrapper
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        //根据条件查询
        //一级分类
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }

        //二级分类
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }

        //根据关注度、日期最新、价格排序
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            //降序排序
            wrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            //降序排序
            wrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            //降序排序
            wrapper.orderByDesc("price");
        }

        //查询
        baseMapper.selectPage(coursePage,wrapper);

        //封装分页对象
        //从分页对象中取出查询出来的数据
        long total = coursePage.getTotal();
        List<EduCourse> records = coursePage.getRecords();
        records.forEach( e -> {
            // 根据课程id获得对应课程的评论数量
            Long comment = baseMapper.getCommentById(e.getId());
            e.setComment(comment);
        });
        long pages = coursePage.getPages();
        long current = coursePage.getCurrent();
        long size = coursePage.getSize();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        // 查询课程评论数量

        Map<String,Object> dataMap = new HashMap<>();

        //封装到map中
        dataMap.put("total",total);
        dataMap.put("records",records);
        dataMap.put("pages",pages);
        dataMap.put("current",current);
        dataMap.put("size",size);
        dataMap.put("hasNext",hasNext);
        dataMap.put("hasPrevious",hasPrevious);


        return dataMap;
    }

    @Override
    public CourseWebVo getBaseInfoById(String courseId) {

        CourseWebVo courseWebVo = baseMapper.getBaseInfoById(courseId);

        return courseWebVo;
    }

    /**
     * 分页查询
     * @param coursePage   分页对象
     * @param courseQuery   分页条件
     */
    @Override
    public void pageQuery(Page<EduCourse> coursePage, CourseQuery courseQuery) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        //按时间降序排序
        wrapper.orderByDesc("view_count");

        if(courseQuery == null){
            //无条件查询
            baseMapper.selectPage(coursePage,wrapper);
            return;
        }

        //程序运行到这里表示courseQuery条件不为空
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        //组装条件
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }

        wrapper.orderByDesc("gmt_create");

        //根据条件进行分页查询
        baseMapper.selectPage(coursePage,wrapper);
    }

    //删除课程
    @Override
    public void deleteCourse(String courseId) {

        //先删除章节中的小节
        videoService.deleteByCourseId(courseId);

        //删除课程章节
        chapterService.deleteByCourseId(courseId);

        //删除课程描述
        descriptionService.removeById(courseId);

        //删除课程本身
        int delete = baseMapper.deleteById(courseId);

        if(delete == 0) {
            throw new GuliException(20001,"删除课程失败");
        }

    }



    //添加课程基本信息
    @Override
    public String addCourseInfo(CourseInfo courseInfo) {

        //1.向课程表中添加数据
        //将CourseInfo转化为EduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo,eduCourse);

        int insert = this.baseMapper.insert(eduCourse);

        if(insert == 0){
            //添加失败
            throw new GuliException(20001,"添加课程信息失败");

        }

        //添加成功

        //要想课程表与课程描述表是一对一的关系，那么它们的主键得保持一致
        String cid = eduCourse.getId();

        //2.向课程描述表中添加信息   课程表与课程描述表是一对一的关系
        //创建描述对象
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        //获得课程信息中的描述
        String description = courseInfo.getDescription();
        eduCourseDescription.setDescription(description);
        //id与课程表的一致
        eduCourseDescription.setId(cid);
        boolean save = descriptionService.save(eduCourseDescription);

        if(!save){
            throw new GuliException(20001,"");
        }

        return cid;

    }

    /**
     * 根据课程id获得课程信息
     * @param courseId
     * @return
     */
    @Override
    public CourseInfo getCourseInfoById(String courseId) {

        //获取课程基本信息
        EduCourse eduCourse = baseMapper.selectById(courseId);


        //将eduCourse转化为courseInfo
        CourseInfo courseInfo = new CourseInfo();
        BeanUtils.copyProperties(eduCourse,courseInfo);

        //获得课程简介
        EduCourseDescription courseDescription = descriptionService.getById(courseId);
        courseInfo.setDescription(courseDescription.getDescription());

        return courseInfo;
    }

    /**
     * 更新课程基本信息
     * @param courseInfo
     */
    @Override
    public void updateCourseInfo(CourseInfo courseInfo) {

        //更新课程基本信息
        //将courseInfo转化为eduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo,eduCourse);

        int update = baseMapper.updateById(eduCourse);
        if(update == 0){
            throw new GuliException(20001,"更新课程信息失败");
        }

        //更新课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfo.getDescription());
        courseDescription.setId(courseInfo.getId());

        boolean update1 = descriptionService.updateById(courseDescription);

        if(!update1){
            throw new GuliException(20001,"更新课程简介失败");
        }

    }

    /**
     * 课程信息发布确认
     */
    @Override
    public CoursePublishVo publishCourse(String courseId) {

        return eduCourseMapper.getCoursePublishVoById(courseId);
    }

    //最终发布，修改课程的状态为：已发布
    @Override
    public void publish(String courseId) {

        EduCourse eduCourse = new EduCourse();

        eduCourse.setId(courseId);
        //设置为已发布状态
        eduCourse.setStatus("Normal");

        baseMapper.updateById(eduCourse);

    }

}
