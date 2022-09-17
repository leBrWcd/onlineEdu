package com.lebrwcd.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.commonutils.R;
import com.lebrwcd.eduorder.entity.Order;
import com.lebrwcd.eduorder.model.dto.QueryDTO;
import com.lebrwcd.eduorder.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-29
 */
@RestController
@RequestMapping("/eduorder/order")
@RefreshScope
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 分页条件查询
     *
     * @param current 当前页
     * @param limit 每页展示几条记录
     * @param dto 查询调条件
     * @return R
     */
    @PostMapping("/page/{current}/{limit}")
    public R pageOrderList(@PathVariable("current") Long current,
                           @PathVariable("limit") Long limit,
                           @RequestBody(required = false) QueryDTO dto) {

        Page<Order> page = new Page<>(current,limit);
        page = orderService.pageQuery(page,dto);
        log.info("日志：分页参数:{},{},total:{}" ,current,limit,page.getTotal());
        return R.ok().data("items",page.getRecords()).data("total",page.getTotal());
    }


    /**
     *      1.生成订单的方法,需要课程信息和用户信息
     */
    @PostMapping("Generic/{courseId}")
    public R genericOrder(@PathVariable String courseId, HttpServletRequest request) {

        //生成订单id
        String orderId = orderService.genericOrder(courseId,request);

        return R.ok().data("orderId",orderId);
    }

    /**
     *     2.根据订单id，查询订单信息
     */
    @GetMapping("getOrderById/{orderId}")
    public R getOrder(@PathVariable String orderId) {

        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);

        return R.ok().data("order",order);
    }

    /**
     * 查询是否购买过
     * @param courseId
     * @param memberId
     * @return
     */
    @GetMapping("isbuyCourse/{courseId}/{memberId}")
    public boolean isbuyCourse(@PathVariable String courseId, @PathVariable String memberId){

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);

        int count = orderService.count(wrapper);

        if(count > 0) {
            return true;
        }else{
            return false;
        }
    }

    @DeleteMapping("{id}")
    public R removeById(@PathVariable("id") Long id) {

        Order order = orderService.getById(id);
        if (order == null) {
            return R.error().message("数据不存在");
        }
        // 逻辑删除
        order.setIsDeleted(true);
        orderService.updateById(order);
        return R.ok();
    }


}

