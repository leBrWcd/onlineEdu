package com.lebrwcd.edusta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lebrwcd.commonutils.R;
import com.lebrwcd.edusta.client.UcenterClient;
import com.lebrwcd.edusta.entity.StatisticsDaily;
import com.lebrwcd.edusta.mapper.StatisticsDailyMapper;
import com.lebrwcd.edusta.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-06-02
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    //统计当日注册人数，采用远程调用ucenter模块方式，获取数据，最后添加到统计数据表
    @Override
    public void registerNumber(String day) {

        R registerCount = ucenterClient.registerCount(day);

        //删除已存在的统计对象
        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.eq("date_calculated", day);
        baseMapper.delete(dayQueryWrapper);

        //获取统计信息
        Integer registerNum = (Integer) registerCount.getData().get("count");
        Integer loginNum = RandomUtils.nextInt(100, 200);
        Integer videoViewNum = RandomUtils.nextInt(100, 200);
        Integer courseNum = RandomUtils.nextInt(100, 200);

        //创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        //插入到数据库
        baseMapper.insert(daily);

    }

    /**
     * 图表统计显示
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Map<String, Object> showData(String type, String begin, String end) {

        //条件查询
        //select type from 表 where between begin and end

        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated",begin,end);
        //只需要查这两个字段，type有4种可能值，取决于前端查询什么，后台数据库就搜什么
        //而不需要搜其他字段，节省时间
        queryWrapper.select("date_calculated",type);

        //查出来的list
        List<StatisticsDaily> dailyList = baseMapper.selectList(queryWrapper);

        //封装两个List

        List<String> DateList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();

        //遍历查询出来的list,取出每个StatisticsDaily
        for (int i = 0; i < dailyList.size(); i++) {

            StatisticsDaily statisticsDaily = dailyList.get(i);
            //封装到日期List（横轴）
            DateList.add(statisticsDaily.getDateCalculated());


            //封装数据List（纵轴）
            switch (type) {
                case "register_num":
                    countList.add(statisticsDaily.getRegisterNum());
                    break;
                case "login_num":
                    countList.add(statisticsDaily.getLoginNum());
                    break;
                case "video_view_num":
                    countList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    countList.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        //最后封装到map
        Map<String,Object> map = new HashMap<>();
        map.put("DateList",DateList);
        map.put("countList",countList);

        //返回map
        return map;

    }
}
