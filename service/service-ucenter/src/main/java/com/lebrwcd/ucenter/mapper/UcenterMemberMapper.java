package com.lebrwcd.ucenter.mapper;

import com.lebrwcd.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-14
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer registerCount(String day);
}
