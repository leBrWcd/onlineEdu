package com.lebrwcd.edusta;/**
 * @author lebrwcd
 * @date 2022/6/2
 * @note
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ClassName StaMain8008
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/6/2
 */
@SpringBootApplication
@ComponentScan("com.lebrwcd")
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling
@MapperScan("com.lebrwcd.edusta.mapper")
public class StaMain8008 {
    public static void main(String[] args) {
        SpringApplication.run(StaMain8008.class,args);
    }
}
