package com.lebrwcd.msmservice.controller;/**
 * @author lebrwcd
 * @date 2022/5/12
 * @note
 */

import com.lebrwcd.commonutils.R;
import com.lebrwcd.msmservice.service.MsmService;
import com.lebrwcd.msmservice.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName MsmApiController
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/12
 */
@RestController
@RequestMapping("/edumsm/msm")
public class MsmApiController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/send/{phone}")
    public R send(@PathVariable String phone) {

        //先去redis中查看短信是否已经发送过了
        String code = (String) redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) { return R.ok(); }

        //没发送过，生成四位验证码
        code = RandomUtils.getFourBitRandom();
        //过期时间
        String expireTime = "5";
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        param.put("expire_at",expireTime);
        boolean isSend = msmService.send(phone,param);

        if(isSend) {
            //如果发送短信成功，存入redis缓存
            redisTemplate.opsForValue().set(phone,code,Integer.parseInt(expireTime), TimeUnit.MINUTES);
            return R.ok();
        }else{
            return R.error().message("发送短信失败");
        }

    }

}
