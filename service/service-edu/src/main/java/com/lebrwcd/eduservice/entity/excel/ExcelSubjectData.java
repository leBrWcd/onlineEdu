package com.lebrwcd.eduservice.entity.excel;/**
 * @author lebrwcd
 * @date 2022/5/3
 * @note
 */

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * ClassName ExcelSubjectData
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/3
 */

/**
 * 与Excel对应
 */
@Data
public class ExcelSubjectData {

    //第一列  一级分类
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    //第二列  二级分类
    @ExcelProperty(index = 1)
    private String twoSubjectName;

}
