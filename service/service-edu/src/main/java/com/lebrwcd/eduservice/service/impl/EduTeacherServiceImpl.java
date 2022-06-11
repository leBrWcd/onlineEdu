package com.lebrwcd.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.eduservice.entity.EduTeacher;
import com.lebrwcd.eduservice.entity.vo.TeacherQuery;
import com.lebrwcd.eduservice.mapper.EduTeacherMapper;
import com.lebrwcd.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-04-24
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");

        /**
         * 如果查询条件为空，无条件查询
         */
        if(teacherQuery == null){
            //baseMapper,相当于以前的dao  service调用dao层
            baseMapper.selectPage(pageParam,wrapper);
            return;
        }

        //程序运行到这里查询条件不为null，判断查询条件，拼接wrapper
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if(!StringUtils.isEmpty(name)){
            //第一个参数写数据库表的字段名
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            //第一个参数写数据库表的字段名
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            //第一个参数写数据库表的字段名
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            //第一个参数写数据库表的字段名
            wrapper.le("gmt_create",end);
        }

        //按时间降序排序
        wrapper.orderByDesc("gmt_create");

        //根据条件进行分页查询
        baseMapper.selectPage(pageParam,wrapper);

    }

    //前台讲师分页
    @Override
    public Map<String, Object> pageTeacher(Page<EduTeacher> teacherPage) {


        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        wrapper.orderByDesc("id");

        baseMapper.selectPage(teacherPage,wrapper);

        //从分页对象中取出查询出来的数据
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        long pages = teacherPage.getPages();
        long current = teacherPage.getCurrent();
        long size = teacherPage.getSize();
        boolean hasNext = teacherPage.hasNext();
        boolean hasPrevious = teacherPage.hasPrevious();

        Map<String,Object> dataMap = new HashMap<>();

        //封装到map中
        dataMap.put("total",total);
        dataMap.put("records",records);
        dataMap.put("pages",pages);
        dataMap.put("current",current);
        dataMap.put("size",size);
        dataMap.put("hasNext",hasNext);
        dataMap.put("hasPrevious",hasPrevious);

        //返回map
        return dataMap;
    }
}
