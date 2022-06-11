package com.lebrwcd.eduservice.listner;/**
 * @author lebrwcd
 * @date 2022/5/3
 * @note
 */

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lebrwcd.eduservice.entity.EduSubject;
import com.lebrwcd.eduservice.entity.excel.ExcelSubjectData;
import com.lebrwcd.eduservice.service.EduSubjectService;
import com.lebrwcd.serviceBase.exceptionhandler.GuliException;

import java.util.Map;

/**
 * ClassName ExcelListner
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/3
 */
public class ExcelListner extends AnalysisEventListener<ExcelSubjectData> {

    public EduSubjectService eduSubjectService;

    public String pid = "";

    public ExcelListner() { }

    //创建有参构造方法，传递subjectService到监听器中用于操作excel数据到数据库中
    public ExcelListner(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    //一行一行读取excel
    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {

        //先判断excel中是否有数据
        if(excelSubjectData == null){
            throw new GuliException(20001,"添加课程分类失败");
        }

        //不为空
        //添加一级分类,先判断数据库中是否已经存在了该一级分类名称
        EduSubject oneSubject = this.existOneSubject(eduSubjectService,excelSubjectData.getOneSubjectName());
        if(oneSubject == null) {
            //此时之前在数据库中不存在的已经添加上去了
            oneSubject = this.save(oneSubject, eduSubjectService, "0", excelSubjectData.getOneSubjectName());
        }


        //二级分类中的parent_id 为一级分类的id
        String pid = oneSubject.getId();

        //添加二级分类，先判断数据库中是否已经存在了该二级分类名称
        EduSubject twoSubject = this.existTwoSubject(eduSubjectService, excelSubjectData.getTwoSubjectName(), pid);
        if(twoSubject==null){
            twoSubject = this.save(twoSubject,eduSubjectService,pid,excelSubjectData.getTwoSubjectName());
        }


    }

    /**
     * 添加课程分类
     * @param subject
     * @param eduSubjectService
     * @param parentId
     * @param title
     */
    public EduSubject save(EduSubject subject,EduSubjectService eduSubjectService,String parentId,String title){

        subject = new EduSubject();
        subject.setParentId(parentId);
        subject.setTitle(title);

        //添加到数据库中
        boolean save = eduSubjectService.save(subject);
        if(save){
            System.out.println("添加成功");
        }

        return subject;
    }



    /**
     * 查询数据库查看一级名称是否已经存在，防止数据库中重复出现
     * @param eduSubjectService
     * @param name
     * @return
     */
    public EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){

        //select * from edu_subject where title = 'name' and paraent_id = '0'
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name).eq("parent_id","0");

        //通过条件查询该一级分类是否存在
        EduSubject oneEduSubject = eduSubjectService.getOne(wrapper);

        //返回查询到的封装对象
        return oneEduSubject;

    }

    /**
     * 查询数据库查看二级名称是否已经存在，防止数据库中重复出现
     * @param eduSubjectService
     * @param name
     * @param pid
     * @return
     */
    public EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid){
        //select * from edu_subject where title = 'name' and paraent_id = 'pid'
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name).eq("parent_id",pid);

        EduSubject twoEduSubject = eduSubjectService.getOne(wrapper);

        return twoEduSubject;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头"+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读取完成");
    }
}
