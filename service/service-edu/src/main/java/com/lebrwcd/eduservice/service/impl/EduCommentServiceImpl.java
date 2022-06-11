package com.lebrwcd.eduservice.service.impl;

import com.lebrwcd.commonutils.JwtUtils;
import com.lebrwcd.commonutils.R;
import com.lebrwcd.commonutils.vo.UcenterMemberVo;
import com.lebrwcd.eduservice.client.MemberClient;
import com.lebrwcd.eduservice.entity.EduComment;
import com.lebrwcd.eduservice.mapper.EduCommentMapper;
import com.lebrwcd.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.serviceBase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-29
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    private MemberClient memberClient;

    @Override
    public boolean saveComment(EduComment eduComment, HttpServletRequest request) {

        //先去请求头中获取有没有token
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        /**
         * String jwtToken = request.getHeader("token");
         *         if(StringUtils.isEmpty(jwtToken)) { return ""; }
         *         Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
         *         Claims claims = claimsJws.getBody();
         *         return (String)claims.get("id");
         */
        if(StringUtils.isEmpty(memberId)) {
            throw new GuliException(20001,"请先登录");
        }

        //如果已经登录
        eduComment.setMemberId(memberId);

        //远程调用service-ucenter中的接口
        UcenterMemberVo memberClientInfo = memberClient.getInfo(memberId);
        if(!StringUtils.isEmpty(memberClientInfo)) {

            eduComment.setAvatar(memberClientInfo.getAvatar());
            eduComment.setNickname(memberClientInfo.getNickname());

            int insert = baseMapper.insert(eduComment);

            if(insert > 0) {
                return true;
            }
        }

        return false;
    }
}
