package com.lebrwcd.eduorder.utils;/**
 * @author lebrwcd
 * @date 2022/5/30
 * @note
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName YamlConstantUtil
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/30
 */
@Component
@ConfigurationProperties("wx.pay")
public class YamlConstantUtil {

    //属性静态化
    public static String appid;
    public static String partner;
    public static String partnerKey;
    public static String notifyurl;

    //get set 不静态
    public  String getAppid() {
        return appid;
    }

    public  void setAppid(String appid) {
        YamlConstantUtil.appid = appid;
    }

    public  String getPartner() {
        return partner;
    }

    public  void setPartner(String partner) {
        YamlConstantUtil.partner = partner;
    }

    public  String getPartnerKey() {
        return partnerKey;
    }

    public  void setPartnerKey(String partnerKey) {
        YamlConstantUtil.partnerKey = partnerKey;
    }

    public  String getNotifyurl() {
        return notifyurl;
    }

    public  void setNotifyurl(String notifyurl) {
        YamlConstantUtil.notifyurl = notifyurl;
    }
}
