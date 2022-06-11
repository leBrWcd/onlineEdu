package com.lebrwcd.eduservice.client;

import com.lebrwcd.commonutils.R;
import com.lebrwcd.commonutils.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lebrwcd
 * @date 2022/5/29
 * @note
 */
@FeignClient(value = "service-ucenter")
@Component
public interface MemberClient {

    @GetMapping("/educenter/member/getInfo/{id}")
    public UcenterMemberVo getInfo(@PathVariable String id);
}
