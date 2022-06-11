package com.lebrwcd.eduservice.entity.course;/**
 * @author lebrwcd
 * @date 2022/5/5
 * @note
 */

import lombok.Data;

import java.util.List;

/**
 * ClassName Chapter
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/5
 */
@Data
public class Chapter {

    private String id;
    private String title;

    private List<Video> children;

}
