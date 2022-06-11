package com.lebrwcd.vod;/**
 * @author lebrwcd
 * @date 2022/5/9
 * @note
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * ClassName VodMain8003
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/9
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.lebrwcd"})
@EnableDiscoveryClient
public class VodMain8003 {

    public static void main(String[] args) {
        SpringApplication.run(VodMain8003.class,args);
    }

}
