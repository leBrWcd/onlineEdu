package com.lebrwcd.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lebrwcd.commonutils.R;
import com.lebrwcd.eduorder.entity.Order;
import com.lebrwcd.eduorder.entity.PayLog;
import com.lebrwcd.eduorder.service.PayLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-29
 */
@RestController
@RequestMapping("/eduorder/paylog")
@Slf4j
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    /**
     * 根据订单id生成微信支付二维码
     * @param orderNo
     * @return
     */
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {

        //map中有二维码地址，也有其他信息
        Map map = payLogService.createNative(orderNo);

        log.info("====生成二维码的map====:",map);

        return R.ok().data(map);
    }

    /**
     * 查询支付状态
     * @param orderNo
     * @return
     */
    @GetMapping("queryStatus/{orderNo}")
    public R queryStatus(@PathVariable String orderNo) {

        Map<String,String> map = payLogService.queryPayStatus(orderNo);

        log.info("****查询支付状态的map****:",map);

        if(map == null){
            return R.error().message("支付出错");
        }
        if(map.get("trade_state").equals("SUCCESS")) {
            //支付成功
            //更新订单表中的状态，以及想交付记录表中新增一记录
            payLogService.updateOrder(map);
            return R.ok().code(20000).message("支付成功");
        }

        return R.ok().code(25000).message("支付中");
    }



}

