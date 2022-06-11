package com.lebrwcd.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lebrwcd.eduservice.entity.EduSubject;
import com.lebrwcd.eduservice.entity.excel.ExcelSubjectData;
import com.lebrwcd.eduservice.entity.subject.OneSubject;
import com.lebrwcd.eduservice.entity.subject.TwoSubject;
import com.lebrwcd.eduservice.listner.ExcelListner;
import com.lebrwcd.eduservice.mapper.EduSubjectMapper;
import com.lebrwcd.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.serviceBase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-03
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    //读取excel
    @Override
    public void excelImport(MultipartFile file, EduSubjectService eduSubjectService) {

        try {
            //1.获取文件输入流
            InputStream inputStream = file.getInputStream();
            //2.读取excel
            EasyExcel.read(inputStream, ExcelSubjectData.class,new ExcelListner(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20002,"添加课程分类失败");
        }

    }

    //得到所有课程，一级分类中包含二级分类
    @Override
    public List<OneSubject> getSubjectList() {

        //1.定义最后返回的数据
        List<OneSubject> data = new ArrayList<>();

        //2.先获得一级分类  select * from edu_subject where parent_id = '0'
        QueryWrapper<EduSubject> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("parent_id","0");
        List<EduSubject> oneSubjects = baseMapper.selectList(queryWrapper1);

        //3.再获得二级分类
        QueryWrapper<EduSubject> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.ne("parent_id",0);
        List<EduSubject> twoSubjects = baseMapper.selectList(queryWrapper2);


        //4.封装一级分类
        //在第二步查出来的每个一级分类，都是一个EduSubject，需要取出来，转换成一级分类OneSubject
        for (int i = 0; i < oneSubjects.size(); i++) {

            //得到每个eduSubject（一级分类）
            EduSubject osubject = oneSubjects.get(i);

            //转化为一级分类对应的bean
            OneSubject oneSubject = new OneSubject();
            //oneSubject.setId(eduSubject.getId());
            //oneSubject.setTitle(eduSubject.getTitle());

            //简写
            BeanUtils.copyProperties(osubject,oneSubject);

            //在一级分类中封装二级分类
            List<TwoSubject> twoSubjectList = new ArrayList<>();

            for(int m = 0;m < twoSubjects.size();m++){

                //在第二步查出来的每个二级分类，都是一个EduSubject，需要取出来，转换成二级分类TwoSubject
                EduSubject tsubject = twoSubjects.get(m);

                //判断二级分类属于哪个一级分类
                if(tsubject.getParentId().equals(oneSubject.getId())){
                    //目标
                    TwoSubject twoSubject = new TwoSubject();

                    BeanUtils.copyProperties(tsubject,twoSubject);

                    //存入该一级分类中的二级分类（只是存入二级分类中，还没有存入一级分类下）
                    twoSubjectList.add(twoSubject);
                }
            }

            //将某个一级分类下的二级分类存入该一级分类中
            oneSubject.setChildren(twoSubjectList);


            //将多个一级分类以及一级分类对应的二级分类存入最终前端要的树形结构
            data.add(oneSubject);

        }

        //返回数据
        return data;
    }
}
