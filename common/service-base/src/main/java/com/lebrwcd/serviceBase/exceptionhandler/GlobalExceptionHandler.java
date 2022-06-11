package com.lebrwcd.serviceBase.exceptionhandler;/**
 * @author lebrwcd
 * @date 2022/4/25
 * @note
 */

import com.lebrwcd.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName GlobalExceptionHandler
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/4/25
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public R ExceptionHandle(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }
}
