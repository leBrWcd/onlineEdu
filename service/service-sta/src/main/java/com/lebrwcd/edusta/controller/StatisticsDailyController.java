package com.lebrwcd.edusta.controller;


import com.lebrwcd.commonutils.R;
import com.lebrwcd.edusta.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author lebrwcd
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/edusta/statistics")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;

    @PostMapping("registerNumber/{day}")
    public R registerNumber(@PathVariable String day) {

        staService.registerNumber(day);

        return R.ok();
    }

    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,
                      @PathVariable String begin,
                      @PathVariable String end) {

        //前端要什么，就返回什么
        /**
         * 在前端统计图的折线图中，需要返回两个json数组，而在java中
         * json串这些都是字符串，在java中能对应前端json数组的是List集合
         * 又要返回两个List集合，所以采用map封装两个list，最后返回
         */
        Map<String,Object> map = staService.showData(type,begin,end);


        return R.ok().data(map);
    }

}

