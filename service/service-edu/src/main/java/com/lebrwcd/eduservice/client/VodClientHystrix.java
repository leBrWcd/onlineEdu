package com.lebrwcd.eduservice.client;/**
 * @author lebrwcd
 * @date 2022/5/10
 * @note
 */

import com.lebrwcd.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ClassName VodClientHystrix
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/10
 */
@Component
public class VodClientHystrix implements VodClient{
    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除失败错误");
    }

    @Override
    public R deleteBatch(List<String> videoSourseIdList) {
        return R.error().message("删除多个视频失败");
    }
}
