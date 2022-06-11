package com.lebrwcd.msmservice;/**
 * @author lebrwcd
 * @date 2022/5/12
 * @note
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName MsmMain
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/12
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.lebrwcd"})
public class MsmMain8005 {

    public static void main(String[] args) {
        SpringApplication.run(MsmMain8005.class,args);
    }
}
