package com.lebrwcd.eduorder.client;

import com.lebrwcd.commonutils.vo.CourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lebrwcd
 * @date 2022/5/30
 * @note
 */
@FeignClient(value = "service-edu")
@Component
public interface CourseClient {

    /**
     * 远程调用edu模块中的方法
     * @param courseId
     * @return
     */
    @GetMapping("/eduservice/course/RemotegetCourse/{courseId}")
    public CourseVo RemoteGetCourse(@PathVariable String courseId);

}
