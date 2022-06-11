package com.lebrwcd.eduservice.entity.vo;/**
 * @author lebrwcd
 * @date 2022/5/7
 * @note
 */

import lombok.Data;

/**
 * ClassName CoursePublishVo
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/7
 */
@Data
public class CoursePublishVo {

    //课程名称
    private String title;
    //课程封面
    private String cover;
    //课时数
    private Integer lessonNum;
    //一级分类名称
    private String subjectLevelOne;
    //二级分类名称
    private String subjectLevelTwo;
    //讲师名称
    private String teacherName;
    //课程价格
    private String price;//只用于显示
}
