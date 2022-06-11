package com.lebrwcd.ucenter.controller;/**
 * @author lebrwcd
 * @date 2022/5/17
 * @note
 */

import com.google.gson.Gson;
import com.lebrwcd.commonutils.JwtUtils;
import com.lebrwcd.serviceBase.exceptionhandler.GuliException;
import com.lebrwcd.ucenter.entity.UcenterMember;
import com.lebrwcd.ucenter.service.UcenterMemberService;
import com.lebrwcd.ucenter.utils.ConstantWxUtils;
import com.lebrwcd.ucenter.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * ClassName WxApiController
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/17
 */

/**
 * 微信扫码登录api
 */
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     * 扫码之后的回调函数
     * @param code   临时票据
     * @param state  个人state，防止恶意登录
     * @return
     */
    @GetMapping("callback")
    public String callback(String code,String state) {

        //第一步：得到授权临时票据code
        //code:051RHwll2dwNa94uOgll2hyPRM0RHwlW
        System.out.println("code:" + code);

        //state:imhelen
        System.out.println("state:" + state);

        //第二步：得到code 和 state后，根据code请求微信提供的固定地址，得到两个值：
        //access_token:访问凭证，openid:微信唯一标识

        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        //完善上面请求地址的参数
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantWxUtils.app_id,
                ConstantWxUtils.app_secret,
                code
        );

        //需要采用httpclient
        try {
            String ResponseData = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken : " + ResponseData);
            //accessToken :
            // {
            // "access_token":"56_U5RQer3Cmw-_prdN3HCYUAHbrAF0EaThiHnFnutXTsUg9JK1O7uVNjOST2YpOjRKBDj_IhKapEM-RtdZyhxdxIelBDz7mZDqR4r91e20FPY",
            // "expires_in":7200,"
            // refresh_token":"56_zKvU2N9tMYLdMVUQ4G63mwxmiMWn3FUGp_hBEWDzdohfG7LAXPL7kkvrMZas6phMC4-Lq8dKkYCM_guil6NonsWCZcHmvVeLevdajzLI6Kw",
            // "openid":"o3_SC5ygB6pcBgKHx-iM6NR837uI",     微信唯一标识
            // "scope":"snsapi_login",
            // "unionid":"oWgGz1IGVpkZyw91BR_U-CzpWVeo"
            // }


            //accessToken最终返回的是json格式的字符串，我们需要解析这串字符串，采用gson工具
            Gson gson = new Gson();
            HashMap map = gson.fromJson(ResponseData, HashMap.class);

            //从map中获取access_token和openid
            String access_token = (String) map.get("access_token");
            String openid = (String) map.get("openid");

            System.out.println("access_token:" + access_token);
            System.out.println("openid :" + openid);


            //接着将扫码人的信息保存到数据库中
            //先根据openid查询数据库是否有该微信用户信息
            UcenterMember ucenterMember = memberService.selectByOpenId(openid);

            //如果没有该用户信息，再去得到
            if (ucenterMember == null) {

                //第三步：再去请求一个固定地址，得到微信扫码人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);

                //还是采用httpClient
                String userInfoData = HttpClientUtils.get(userInfoUrl);

                //解析
                HashMap<String, Object> userMap = gson.fromJson(userInfoData, HashMap.class);
                String nickname = (String) userMap.get("nickname");
                String headimgurl = (String) userMap.get("headimgurl");

                //添加到数据库中
                ucenterMember = new UcenterMember();

                ucenterMember.setNickname(nickname);
                ucenterMember.setAvatar(headimgurl);
                ucenterMember.setOpenid(openid);

                boolean save = memberService.save(ucenterMember);

                if (!save) {
                    throw new GuliException(20001, "保存用户失败");
                }

            }

            //使用jwt根据ucentermember对象生成token字符串
            String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

            //存入cookie
            //CookieUtils.setCookie(request, response, "guli_jwt_token", token);
            //因为端口号不同存在跨域问题，cookie不能跨域，所以这里使用url重写

            //返回首页。通过路径传递token字符串
            return "redirect:http://localhost:3000?token="+token;

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"登录失败");
        }
    }

    /**
     * 微信二维码
     * @param session
     * @return
     */
    @GetMapping("login")
    public String login(HttpSession session) {

        //微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                          "?appid=%s" +
                          "&redirect_uri=%s" +
                          "&response_type=code" +
                          "&scope=snsapi_login" +
                          "&state=%s" +
                          "#wechat_redirect";

        //回调地址
        String redirectUrl = ConstantWxUtils.redirect_url;
        System.out.println(ConstantWxUtils.redirect_url);
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //防止csrf攻击（跨站请求伪造攻击）
        String state = "imhelen";
        System.out.println("state = " + state);

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantWxUtils.app_id,
                redirectUrl,
                state
        );
        //重定向到这个地址
        return "redirect:" + qrcodeUrl;

    }
}
