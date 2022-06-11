package com.lebrwcd.eduservice;/**
 * @author lebrwcd
 * @date 2022/4/24
 * @note
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * ClassName EduMain8001
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/4/24
 */
@SpringBootApplication
@Slf4j
@EnableSwagger2
@EnableDiscoveryClient  //服务注册与发现
@EnableFeignClients  //服务调用
@ComponentScan(basePackages = {"com.lebrwcd"})
public class EduMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(EduMain8001.class,args);
    }
}
