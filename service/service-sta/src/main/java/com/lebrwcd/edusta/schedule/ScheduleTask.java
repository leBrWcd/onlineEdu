package com.lebrwcd.edusta.schedule;/**
 * @author lebrwcd
 * @date 2022/6/2
 * @note
 */

import com.lebrwcd.edusta.service.StatisticsDailyService;
import com.lebrwcd.edusta.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName ScheduleTask
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/6/2
 */
@Component
public class ScheduleTask {

    @Autowired
    private StatisticsDailyService dailyService;
        /**
         * 测试
         * 每天七点到二十三点每五秒执行一次
         */
    @Scheduled(cron = "0/5 * * * * ?")
    public void task1() {

        System.out.println("测试定时任务*********++++++++++++*****执行了");

    }

        /**
         * 每天凌晨1点执行定时
         18
         */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {

        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        dailyService.registerNumber(day);
    }
}
