package com.lebrwcd.eduservice.controller;


import com.lebrwcd.commonutils.R;
import com.lebrwcd.eduservice.entity.subject.OneSubject;
import com.lebrwcd.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-03
 */
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 添加课程分类
     * @param file  excel文件
     * @return
     */
    @PostMapping("addSubject")
    public R readExcel(MultipartFile file){

        //读取,有异常退出
        eduSubjectService.excelImport(file,eduSubjectService);

        return R.ok();

    }

    /**
     * 查询所有分类，一级分类下面包含二级分类
     * @return
     */
    @GetMapping("subjectList")
    public R list(){

        //一级分类包含二级分类
        List<OneSubject> list = eduSubjectService.getSubjectList();

        return R.ok().data("list",list);

    }



}

