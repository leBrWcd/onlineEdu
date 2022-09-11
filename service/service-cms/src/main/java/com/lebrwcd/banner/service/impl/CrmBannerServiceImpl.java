package com.lebrwcd.banner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.banner.entity.CrmBanner;
import com.lebrwcd.banner.mapper.CrmBannerMapper;
import com.lebrwcd.banner.model.dto.BannerQueryDTO;
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
        wrapper.last("limit 4");

        List<CrmBanner> list = baseMapper.selectList(wrapper);

        return list;

    }

    /**
     * 条件查询
     * @param page 分页对象
     * @param dto 查询条件
     */
    @Override
    public void pageQuery(Page<CrmBanner> page, BannerQueryDTO dto) {



    }
}
