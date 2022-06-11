package com.lebrwcd.service.impl;/**
 * @author lebrwcd
 * @date 2022/5/2
 * @note
 */

import com.aliyun.oss.OSSClient;
import com.lebrwcd.service.OssService;
import com.lebrwcd.utils.ConstantYamlUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * ClassName OssServiceImp
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/2
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String upload(MultipartFile file) {

        //获取阿里云存储相关变量
        String endpoint = ConstantYamlUtil.endpoint;
        String accessKeyId = ConstantYamlUtil.keyId;
        String accessKeySecret = ConstantYamlUtil.keySecret;
        String bucketName = ConstantYamlUtil.bucketName;

        //返回的url
        String url = "";

        //创建oss实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);


        try {
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();

            //防止文件重复
            String uuid = UUID.randomUUID().toString().substring(25).replaceAll("-", "");

            //文件上传分类，根据日期分类
            String datePath = new DateTime().toString("yyyy/MM/dd");

            //获取文件名称
            String filename = file.getOriginalFilename();

            //拼接文件名称
            filename = datePath +"/" + uuid + filename;

            //调用oss方法实现上传
            ossClient.putObject(bucketName,filename,inputStream);

            //关闭ossClient
            ossClient.shutdown();

            //把上传之后的文件路径返回
            url = "http://" + bucketName +"."+endpoint+"/"+filename;

            return url;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
