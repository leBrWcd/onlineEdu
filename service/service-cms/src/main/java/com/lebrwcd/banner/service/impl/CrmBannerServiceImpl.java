package com.lebrwcd.banner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lebrwcd.banner.entity.CrmBanner;
import com.lebrwcd.banner.mapper.CrmBannerMapper;
import com.lebrwcd.banner.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-11
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(key = "'bannerList'",value = "banner")  //redis缓存
    @Override
    public List<CrmBanner> queryBanner() {

        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        //根据id降序排列
        wrapper.orderByDesc("id");
        //取出前两条数据
        wrapper.last("limit 2");

        List<CrmBanner> list = baseMapper.selectList(wrapper);

        return list;

    }
}
