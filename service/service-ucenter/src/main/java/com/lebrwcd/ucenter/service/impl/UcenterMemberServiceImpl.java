package com.lebrwcd.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lebrwcd.commonutils.JwtUtils;
import com.lebrwcd.commonutils.MD5Utils;
import com.lebrwcd.serviceBase.exceptionhandler.GuliException;
import com.lebrwcd.ucenter.entity.UcenterMember;
import com.lebrwcd.ucenter.entity.vo.RegisterVo;
import com.lebrwcd.ucenter.mapper.UcenterMemberMapper;
import com.lebrwcd.ucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.ucenter.entity.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-14
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public String login(LoginVo loginVo) {

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //先对手机和密码进行非空验证
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001,"手机号和密码不能为空");
        }

        //接着判断手机是否正确，有没有在数据库中
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",loginVo.getMobile());
        UcenterMember ucenterMember = this.baseMapper.selectOne(wrapper);

        if(ucenterMember == null) {
            throw new GuliException(20001,"该手机号未注册");
        }

        //运行到这里表示手机号密码不为空，且手机号正确，验证密码是否正确
        if(!MD5Utils.getMD5(password).equals(ucenterMember.getPassword())) {
            throw new GuliException(20001,"密码不正确");
        }

        //到这里，手机号，密码校验完成，判断该用户是否被禁用
        if(ucenterMember.getIsDisabled()) {
            throw new GuliException(20001,"该用户被禁用！");
        }

        //程序运行到这里表示登录成功，生成token
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return token;
    }

    //注册
    @Override
    public void register(RegisterVo registerVo) {

        //获取参数
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String code = registerVo.getCode();
        String password = registerVo.getPassword();

        //非空验证
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001,"注册失败");
        }

        //到这里表示输入不为空，验证验证码是否正确，从redis中取
        String MobileCode = (String)redisTemplate.opsForValue().get(mobile);
        if(!code.equals(MobileCode)) {
            throw new GuliException(20001,"验证码失效或不正确");
        }

        //判断手机号是否重复
        Integer count = this.baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if(count.intValue() > 0) {
            throw new GuliException(20001,"该手机号已经存在");
        }

        //到这里表示一切正确，将用户数据保存到数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setPassword(MD5Utils.getMD5(password));
        member.setNickname(nickname);
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        boolean save = this.save(member);

        if(!save) {
            throw new GuliException(20001,"注册失败");
        }

    }

    //根据openid从数据库获取扫码人信息
    @Override
    public UcenterMember selectByOpenId(String openid) {

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();

        wrapper.eq("openid",openid);

        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);

        return ucenterMember;
    }

    //统计当日注册人数
    @Override
    public Integer registerCount(String day) {

        Integer count = baseMapper.registerCount(day);

        return count;
    }
}
