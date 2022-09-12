package com.lebrwcd.banner.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.banner.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lebrwcd.banner.model.dto.BannerQueryDTO;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-11
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> queryBanner();

    Page<CrmBanner> pageQuery(Page<CrmBanner> objectPage, BannerQueryDTO dto);
}
