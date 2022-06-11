package com.lebrwcd.ucenter.utils;/**
 * @author lebrwcd
 * @date 2022/5/17
 * @note
 */

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName ConstantWxUtils
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/17
 */
@Component
@ConfigurationProperties("wx.open")
public class ConstantWxUtils implements InitializingBean {

    public static String app_id;

    public static String app_secret;

    public static String redirect_url;

    public  String getApp_id() {
        return app_id;
    }

    public  void setApp_id(String app_id) {
        ConstantWxUtils.app_id = app_id;
    }

    public  String getApp_secret() {
        return app_secret;
    }

    public  void setApp_secret(String app_secret) {
        ConstantWxUtils.app_secret = app_secret;
    }

    public  String getRedirect_url() {
        return redirect_url;
    }

    public  void setRedirect_url(String redirect_url) {
        ConstantWxUtils.redirect_url = redirect_url;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
