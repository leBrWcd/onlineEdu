package com.lebrwcd.eduorder.mapper;

import com.lebrwcd.eduorder.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lebrwcd.eduorder.model.dto.QueryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-29
 */
public interface OrderMapper extends BaseMapper<Order> {

    List<Order> pageQuery(@Param("num") Long num,
                          @Param("size") long size,
                          @Param("map") QueryDTO dto);
}
