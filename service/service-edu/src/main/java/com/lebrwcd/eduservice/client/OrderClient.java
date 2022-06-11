package com.lebrwcd.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lebrwcd
 * @date 2022/5/31
 * @note
 */
@Component
@FeignClient(value = "service-order")
public interface OrderClient {

    @GetMapping("/eduorder/order/isbuyCourse/{courseId}/{memberId}")
    public boolean isbuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);

}
