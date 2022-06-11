package com.lebrwcd.vod.utils;/**
 * @author lebrwcd
 * @date 2022/5/9
 * @note
 */

/**
 * ClassName initVodUtil
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/9
 */

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 初始化客户端
 */
public class VodUtil {

    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {

        // 点播服务接入区
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;

    }

}
