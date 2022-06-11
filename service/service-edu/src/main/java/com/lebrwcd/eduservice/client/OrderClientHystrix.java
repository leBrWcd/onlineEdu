package com.lebrwcd.eduservice.client;/**
 * @author lebrwcd
 * @date 2022/5/31
 * @note
 */

import org.springframework.stereotype.Component;

/**
 * ClassName OrderClientHystrix
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/31
 */
@Component
public class OrderClientHystrix implements OrderClient{
    @Override
    public boolean isbuyCourse(String courseId, String memberId) {


        return false;
    }
}
