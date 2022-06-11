package com.lebrwcd.edusta.client;/**
 * @author lebrwcd
 * @date 2022/6/2
 * @note
 */

import com.lebrwcd.commonutils.R;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * ClassName UcenterClientHystrix
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/6/2
 */
@Component
public class UcenterClientHystrix implements UcenterClient{
    @Override
    public R registerCount(String day) {

        return R.error().message("生成数据失败");
    }
}
