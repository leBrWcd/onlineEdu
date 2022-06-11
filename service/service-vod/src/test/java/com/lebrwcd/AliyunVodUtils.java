package com.lebrwcd;/**
 * @author lebrwcd
 * @date 2022/5/8
 * @note
 */

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * ClassName AliyunVodUtils
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/8
 */
public class AliyunVodUtils {

    public static DefaultAcsClient initVoidClient(String accessKeyId,String accessKeySecret) throws ClientException {

        String regionId = "cn-shanghai"; //点播服务接入区域

        DefaultProfile profile = DefaultProfile.getProfile(regionId,accessKeyId,accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        return client;

    }
}
