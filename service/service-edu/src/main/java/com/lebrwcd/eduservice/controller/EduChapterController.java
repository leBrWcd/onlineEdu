package com.lebrwcd.eduservice.controller;


import com.lebrwcd.commonutils.R;
import com.lebrwcd.eduservice.entity.EduChapter;
import com.lebrwcd.eduservice.entity.course.Chapter;
import com.lebrwcd.eduservice.service.EduChapterService;
import com.lebrwcd.eduservice.service.impl.EduChapterServiceImpl;
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
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 根据课程id查出对应章节和小节
     * @param courseId
     * @return
     */
    @GetMapping("getAllChapter/{courseId}")
    public R getAllChapter(@PathVariable("courseId") String courseId){

        List<Chapter> list = eduChapterService.getChapeters(courseId);

        return R.ok().data("list",list);
    }

    /**
     * 添加章节
     * @param eduChapter
     * @return
     */
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){

        eduChapterService.save(eduChapter);

        return R.ok();
    }

    /**
     * 根据章节id获得章节信息
     * @param
     * @return
     */
    @GetMapping("/getChapter/{chapterId}")
    public R getChapter(@PathVariable String chapterId){

        EduChapter eduChapter = eduChapterService.getById(chapterId);

        return R.ok().data("chapter",eduChapter);
    }

    /**
     * 更新
     * @param eduChapter
     * @return
     */
    @PostMapping("update")
    public R updateChapter(@RequestBody EduChapter eduChapter){

        eduChapterService.updateById(eduChapter);

        return R.ok();
    }

    /**
     * 删除章节，若章节下面有小节，则不删除
     * @param chapterId
     * @return
     */
    @DeleteMapping("{chapterId}")
    public R delete(@PathVariable String chapterId){

        boolean flag = eduChapterService.deleteById(chapterId);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }
}

