package com.lebrwcd.banner.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.banner.entity.CrmBanner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lebrwcd.banner.model.dto.BannerQueryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 首页banner表 Mapper 接口
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-11
 */
public interface CrmBannerMapper extends BaseMapper<CrmBanner> {

    List<CrmBanner> pageQuery(@Param("num") Long num,
                              @Param("size") Long size,
                              @Param("map") BannerQueryDTO dto);
}
