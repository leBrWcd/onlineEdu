package com.lebrwcd.serviceBase.exceptionhandler;/**
 * @author lebrwcd
 * @date 2022/4/26
 * @note
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName MyException
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/4/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuliException extends RuntimeException{

    //状态码
    private Integer code;

    private String msg;

}
