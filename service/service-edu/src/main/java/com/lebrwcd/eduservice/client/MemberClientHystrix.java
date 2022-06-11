package com.lebrwcd.eduservice.client;/**
 * @author lebrwcd
 * @date 2022/5/31
 * @note
 */

import com.lebrwcd.commonutils.vo.UcenterMemberVo;
import org.springframework.stereotype.Component;

/**
 * ClassName MemberClientHystrix
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/31
 */
@Component
public class MemberClientHystrix implements MemberClient{
    @Override
    public UcenterMemberVo getInfo(String id) {
        return null;
    }
}
