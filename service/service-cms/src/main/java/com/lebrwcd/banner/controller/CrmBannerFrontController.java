package com.lebrwcd.banner.controller;/**
 * @author lebrwcd
 * @date 2022/5/11
 * @note
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lebrwcd.banner.entity.CrmBanner;
import com.lebrwcd.banner.service.CrmBannerService;
import com.lebrwcd.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName CrmBannerFrontController
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/11
 */
@RestController
@RequestMapping("/educms/bannerfront")
public class CrmBannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 获取banner
     * @return
     */
    @GetMapping("getBanner")
    public R getAll() {

        List<CrmBanner> list = bannerService.queryBanner();

        return R.ok().data("list",list);
    }

}
