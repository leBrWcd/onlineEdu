package com.lebrwcd.eduorder.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lebrwcd.eduorder.model.dto.QueryDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-29
 */
public interface OrderService extends IService<Order> {

    String genericOrder(String courseId, HttpServletRequest request);

    Page<Order> pageQuery(Page<Order> page, QueryDTO dto);
}
