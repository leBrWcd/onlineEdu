package com.lebrwcd.eduservice.service;

import com.lebrwcd.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-04
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteByCourseId(String courseId);
}
