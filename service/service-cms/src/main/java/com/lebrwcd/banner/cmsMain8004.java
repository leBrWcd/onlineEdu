package com.lebrwcd.banner;/**
 * @author lebrwcd
 * @date 2022/5/11
 * @note
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName cmsMain8004
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/11
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.lebrwcd"})
@MapperScan("com.lebrwcd.banner.mapper")
@EnableCaching
public class cmsMain8004 {

    public static void main(String[] args) {
        SpringApplication.run(cmsMain8004.class,args);
    }
}
