package com.lebrwcd.acl;/**
 * @author lebrwcd
 * @date 2022/6/5
 * @note
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * ClassName AclMain8009
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/6/5
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@ComponentScan("com.lebrwcd")
@MapperScan("com.lebrwcd.acl.mapper")
public class AclMain8009 {
    public static void main(String[] args) {
        SpringApplication.run(AclMain8009.class,args);
    }
}
