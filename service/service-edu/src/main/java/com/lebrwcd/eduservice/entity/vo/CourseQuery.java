package com.lebrwcd.eduservice.entity.vo;/**
 * @author lebrwcd
 * @date 2022/5/8
 * @note
 */

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName CourseQuery
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/8
 */
@Data
public class CourseQuery implements Serializable {

    private String title;

    private String status;

}
