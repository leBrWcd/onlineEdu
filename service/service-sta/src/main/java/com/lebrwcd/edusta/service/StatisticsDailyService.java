package com.lebrwcd.edusta.service;

import com.lebrwcd.edusta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-06-02
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void registerNumber(String day);

    Map<String, Object> showData(String type, String begin, String end);
}
