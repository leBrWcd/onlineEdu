package com.lebrwcd.eduservice.service;

import com.lebrwcd.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lebrwcd.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-03
 */
public interface EduSubjectService extends IService<EduSubject> {

    //读取excel,第一个参数是excel文件，第二个文件是传入impl中，
    // Impl中要用到监听器，但监听器无法管理我们自己new的监听器，所以我们自己手动注入service，在监听器中使用
    void excelImport(MultipartFile file, EduSubjectService eduSubjectService);


    //获得一级分类，一级分类中又包含二级分类
    List<OneSubject> getSubjectList();
}
