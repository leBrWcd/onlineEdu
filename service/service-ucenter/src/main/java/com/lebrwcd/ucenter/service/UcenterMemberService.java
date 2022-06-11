package com.lebrwcd.ucenter.service;

import com.lebrwcd.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lebrwcd.ucenter.entity.vo.LoginVo;
import com.lebrwcd.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-14
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    UcenterMember selectByOpenId(String openid);

    Integer registerCount(String day);
}
