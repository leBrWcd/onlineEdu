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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台banner管理
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-11
 */
@RestController
@RefreshScope
@Slf4j
@RequestMapping("/educms/banner")
public class CrmBannerAdminController {


    @Autowired
    private CrmBannerService bannerService;

    /**
     * 条件分页查询
     * @param current 当前页
     * @param limit 每页显示条数
     * @param dto 查询条件
     * @return R
     */
    @PostMapping("/page/{current}/{limit}")
    public R page(@PathVariable("current") Long current,
                  @PathVariable("limit") Long limit,
                  @RequestBody(required = false) BannerQueryDTO dto) {
        Page<CrmBanner> page = new Page<>(current,limit);
        page = bannerService.pageQuery(page,dto);
        log.info("日志：分页参数:{},{},total:{}" ,current,limit,page.getTotal());
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

