package com.lebrwcd.bean;/**
 * @author lebrwcd
 * @date 2022/5/3
 * @note
 */

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * ClassName ExcelTest
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/3
 */
@Data
public class ExcelTest {


    @ExcelProperty(value = "学号")
    private String one;

    @ExcelProperty(value = "姓名")
    private String two;

}
