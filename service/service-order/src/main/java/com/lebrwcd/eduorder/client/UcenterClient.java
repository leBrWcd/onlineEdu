package com.lebrwcd.eduorder.client;

import com.lebrwcd.commonutils.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lebrwcd
 * @date 2022/5/30
 * @note
 */
@FeignClient(value = "service-ucenter")
@Component
public interface UcenterClient {

    @GetMapping("/educenter/member/getInfo/{id}")
    public UcenterMemberVo getInfo(@PathVariable String id);

}
