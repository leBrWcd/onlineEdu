package com.lebrwcd.edusta.client;/**
 * @author lebrwcd
 * @date 2022/6/2
 * @note
 */

import com.lebrwcd.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName UcenterClient
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/6/2
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @GetMapping("/educenter/member/registerCount/{day}")
    public R registerCount (@PathVariable String day);

}
