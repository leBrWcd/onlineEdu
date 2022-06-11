package com.lebrwcd.ucenter.controller;


import com.lebrwcd.commonutils.JwtUtils;
import com.lebrwcd.commonutils.R;
import com.lebrwcd.commonutils.vo.UcenterMemberVo;
import com.lebrwcd.ucenter.entity.UcenterMember;
import com.lebrwcd.ucenter.entity.vo.RegisterVo;
import com.lebrwcd.ucenter.service.UcenterMemberService;
import com.lebrwcd.ucenter.entity.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-14
 */
@RestController
@RequestMapping("/educenter/member")
@Slf4j
public class UcenterMemberController {


    @Autowired
    private UcenterMemberService memberService;

    /**
     * 登录
     * @param loginVo
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo) {

        String token = memberService.login(loginVo);
        return R.ok().data("token",token);
    }

    /**
     *  注册       *
     */
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo) {

        memberService.register(registerVo);

        return R.ok();

    }

    /**
     * 根据token获取用户信息
     * @param request
     * @return
     */
    @GetMapping("/getUserInfo")
    public R getUserInfo(HttpServletRequest request) {
        //调用jwt工具类，根据request对象获取头信息，返回用户id
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        log.info("memberId:" + memberIdByJwtToken);
        //根据用户id查询用户信息
        UcenterMember member = memberService.getById(memberIdByJwtToken);
        log.info("member:"+member);
        return R.ok().data("userInfo",member);
    }

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @GetMapping("getInfo/{id}")
    public UcenterMemberVo getInfo(@PathVariable String id) {

        UcenterMember member = memberService.getById(id);
        UcenterMemberVo ucenterMemberVo = new UcenterMemberVo();

        BeanUtils.copyProperties(member,ucenterMemberVo);

        return ucenterMemberVo;
    }

    /**
     * 统计当日注册人数
     * @param day
     * @return
     */
    @GetMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day) {

        Integer registerCount = memberService.registerCount(day);

        return R.ok().data("count",registerCount);
    }

}

