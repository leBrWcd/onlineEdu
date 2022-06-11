package com.lebrwcd.vod.utils;/**
 * @author lebrwcd
 * @date 2022/5/9
 * @note
 */

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName ConstantYamlUtil
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/9
 */
@Component
@ConfigurationProperties("aliyun.vod.file")
public class ConstantYamlUtil implements InitializingBean {

    public static String keyId;

    public static String keySecret;

    public  String getKeyId() {
        return keyId;
    }

    public  void setKeyId(String keyId) {
        ConstantYamlUtil.keyId = keyId;
    }

    public  String getKeySecret() {
        return keySecret;
    }

    public  void setKeySecret(String keySecret) {
        ConstantYamlUtil.keySecret = keySecret;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
