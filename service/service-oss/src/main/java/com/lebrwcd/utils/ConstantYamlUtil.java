package com.lebrwcd.utils;/**
 * @author lebrwcd
 * @date 2022/5/2
 * @note
 */

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName ConstantYamlUtil
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/2
 */
@Component
@ConfigurationProperties("aliyun.oss.file")
public class ConstantYamlUtil implements InitializingBean {

    public static String endpoint;

    public static String keyId;

    public static String keySecret;

    public static String bucketName;

    public  String getEndpoint() {
        return endpoint;
    }

    public  void setEndpoint(String endpoint) {
        ConstantYamlUtil.endpoint = endpoint;
    }

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

    public  String getBucketName() {
        return bucketName;
    }

    public  void setBucketName(String bucketName) {
        ConstantYamlUtil.bucketName = bucketName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
