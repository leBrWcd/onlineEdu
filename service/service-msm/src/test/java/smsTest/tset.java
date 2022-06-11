package smsTest;/**
 * @author lebrwcd
 * @date 2022/5/14
 * @note
 */

import com.lebrwcd.msmservice.utils.RandomUtils;
import org.apache.http.HttpResponse;
import org.junit.Test;
import com.lebrwcd.msmservice.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName tset
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/14
 */
public class tset {

    @Test
    public void testf() {
        System.out.println(RandomUtils.getFourBitRandom());
    }

    @Test
    public void test() {
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "aa128f0f5195477ca0b94e81f9e0c416";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:1441");
        bodys.put("phone_number", "19128947414");
        bodys.put("template_id", "TPL_0000");

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
