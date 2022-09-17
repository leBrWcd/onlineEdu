package com.lebrwcd.eduorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.commonutils.JwtUtils;
import com.lebrwcd.commonutils.R;
import com.lebrwcd.commonutils.vo.CourseVo;
import com.lebrwcd.commonutils.vo.UcenterMemberVo;
import com.lebrwcd.eduorder.client.CourseClient;
import com.lebrwcd.eduorder.client.UcenterClient;
import com.lebrwcd.eduorder.entity.Order;
import com.lebrwcd.eduorder.mapper.OrderMapper;
import com.lebrwcd.eduorder.model.dto.QueryDTO;
import com.lebrwcd.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-29
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 生成订单id
     * @param courseId
     * @param request
     * @return
     */
    @Override
    public String genericOrder(String courseId, HttpServletRequest request) {

        //通过课程id远程获取课程信息
        CourseVo courseVo = courseClient.RemoteGetCourse(courseId);


        //课程用户id远程获取用户信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMemberVo memberVo = ucenterClient.getInfo(memberId);

        //创建order对象，向order对象里面设置需要的数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.RandomOrder());
        order.setCourseCover(courseVo.getCover());
        order.setCourseId(courseId);
        order.setCourseTitle(courseVo.getTitle());
        order.setTeacherName(courseVo.getTeacherName());
        order.setMemberId(memberId);
        order.setMobile(memberVo.getMobile());
        order.setNickname(memberVo.getNickname());
        order.setPayType(1);  //订单状态(0:未支付，1:已支付)
        order.setStatus(0);   // 支付类型 微信: 1

        baseMapper.insert(order);

        //返回订单id
        return order.getOrderNo();
    }

    /**
     *  分页查询
     * @param page 分页对象
     * @param dto 查询条件
     * @return Page<Order>
     */
    @Override
    public Page<Order> pageQuery(Page<Order> page, QueryDTO dto) {

        Long num = (page.getCurrent() - 1) * page.getSize();
        List<Order> crmBanners = baseMapper.pageQuery(num,page.getSize(),dto);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        if (dto.getOrderNo() != "") {
            wrapper.like("order_no",dto.getOrderNo());
        }
        if (dto.getStatus() != null) {
            wrapper.eq("status",dto.getStatus());
        }
        if (dto.getGmtCreate() != null) {
            wrapper.eq("gmt_create",dto.getGmtCreate());
        }
        if (dto.getGmtModified() != null) {
            wrapper.eq("gmt_modified",dto.getGmtModified());
        }
        wrapper.eq("is_deleted",0);
        Integer count = baseMapper.selectCount(wrapper);

        if (crmBanners != null) {
            page.setRecords(crmBanners);
        }
        if (count > 0) {
            page.setTotal(count);
        }
        return page;
    }
}
