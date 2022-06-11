package com.lebrwcd.eduservice.entity.subject;/**
 * @author lebrwcd
 * @date 2022/5/4
 * @note
 */

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName OneSubject
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/4
 */
@Data
public class OneSubject {

    private String id;
    private String title;  //label

    //树形结构
    //一个一级分类包含1个或多个二级分类
    //data2: [{
    //        id: 1,
    //        label: '一级分类 1',
    //        children: [{
    //          id: 4,
    //          label: '二级分类 1-1',
    //        }
    List<TwoSubject> children = new ArrayList<>();
}
