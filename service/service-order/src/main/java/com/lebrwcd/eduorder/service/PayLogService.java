package com.lebrwcd.eduorder.service;

import com.lebrwcd.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-29
 */
public interface PayLogService extends IService<PayLog> {

    Map createNative(String orderId);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrder(Map<String, String> map);
}
