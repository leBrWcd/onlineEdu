package com.lebrwcd.eduorder;/**
 * @author lebrwcd
 * @date 2022/5/29
 * @note
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName OrderMain8007
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/29
 */
@SpringBootApplication
@EnableFeignClients
@MapperScan("com.lebrwcd.eduorder.mapper")
@ComponentScan(basePackages = {"com.lebrwcd"})
@EnableDiscoveryClient
public class OrderMain8007 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMain8007.class,args);
    }

}
