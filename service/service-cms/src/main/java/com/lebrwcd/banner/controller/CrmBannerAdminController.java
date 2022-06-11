package com.lebrwcd.banner.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.banner.entity.CrmBanner;
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
@RequestMapping("/eduservice/banner")
public class CrmBannerAdminController {


    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("/page/{current}/{limit}")
    public R page(@PathVariable("current") Long current,
                  @PathVariable("limit") Long limit) {


        Page<CrmBanner> page = new Page<>(current,limit);

        bannerService.page(page,null);


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
        bannerService.removeById(id);

        return R.ok();
    }


}

