package com.lebrwcd.gateway;/**
 * @author lebrwcd
 * @date 2022/6/3
 * @note
 */

import com.alibaba.nacos.api.annotation.NacosProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

/**
 * ClassName GateWay8222
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/6/3
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateWay8222 {

    public static void main(String[] args) {
        SpringApplication.run(GateWay8222.class,args);
    }
}
