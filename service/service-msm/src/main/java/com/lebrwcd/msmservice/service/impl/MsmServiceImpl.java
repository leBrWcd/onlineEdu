package com.lebrwcd.msmservice.service.impl;/**
 * @author lebrwcd
 * @date 2022/5/12
 * @note
 */

import com.alibaba.fastjson.JSONObject;
import com.lebrwcd.msmservice.service.MsmService;
import com.lebrwcd.msmservice.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName MsmServiceImpl
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/12
 */
@Service
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean send(String phone, Map<String, Object> param) {

        if(StringUtils.isEmpty(phone)) { return false; }


        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "aa128f0f5195477ca0b94e81f9e0c416";
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<>();
        Map<String, String> bodys = new HashMap<>();

        bodys.put("content", JSONObject.toJSONString(param));
        bodys.put("phone_number", phone);
        bodys.put("template_id", "TPL_0001");


        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            if(response.getStatusLine().getStatusCode() == 200) { return true; }


            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
