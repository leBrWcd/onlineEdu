package com.lebrwcd.banner.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.banner.entity.CrmBanner;
import com.lebrwcd.banner.model.dto.BannerQueryDTO;
import com.lebrwcd.banner.service.CrmBannerService;
import com.lebrwcd.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;

/**
 * <p>
 * 后台banner管理
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-11
 */
@RestController
@RequestMapping("/educms/banner")
public class CrmBannerAdminController {


    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("/page/{current}/{limit}")
    public R page(@PathVariable("current") Long current,
                  @PathVariable("limit") Long limit,
                  @RequestBody BannerQueryDTO dto) {

        Page<CrmBanner> page = new Page<>(current,limit);

        bannerService.pageQuery(page,dto);


        //LambdaQueryWrapper<CrmBanner> queryWrapper = new LambdaQueryWrapper<>();
        //queryWrapper.eq(CrmBanner::getIsDeleted,"0")
        //        .orderByAsc(CrmBanner::getSort);
        //IPage<CrmBanner> page1 = bannerService.page(page, queryWrapper);

        return R.ok().data("items",page.getRecords()).data("total",page.getTotal());
    }

    @PostMapping("save")
    public R addBanner(@RequestBody CrmBanner crmBanner) {

        bannerService.save(crmBanner);

        return R.ok();

    }

    @GetMapping("{id}")
    public R getById(@PathVariable String id) {
        CrmBanner crmBanner = bannerService.getById(id);

        return R.ok().data("banner",crmBanner);
    }

    @PostMapping("update")
    public R update(@RequestBody CrmBanner crmBanner) {

        bannerService.updateById(crmBanner);

        return R.ok();

    }

    @DeleteMapping("{id}")
    public R delete(@PathVariable String id) {
        //逻辑删除
        // 先查询待删除的banner是否存在
        CrmBanner banner = bannerService.getById(id);
        if (banner != null) {
            banner.setIsDeleted(true);
            bannerService.updateById(banner);
        }
        return R.ok();
    }


}

