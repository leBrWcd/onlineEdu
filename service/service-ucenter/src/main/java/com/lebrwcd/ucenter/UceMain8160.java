package com.lebrwcd.ucenter;/**
 * @author lebrwcd
 * @date 2022/5/14
 * @note
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName UceMain8006
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/14
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.lebrwcd")
@MapperScan("com.lebrwcd.ucenter.mapper")
public class UceMain8160 {

    public static void main(String[] args) {
        SpringApplication.run(UceMain8160.class,args);
    }
}
