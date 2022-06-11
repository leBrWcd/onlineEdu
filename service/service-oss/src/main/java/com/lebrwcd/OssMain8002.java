package com.lebrwcd;/**
 * @author lebrwcd
 * @date 2022/5/2
 * @note
 */

import com.lebrwcd.utils.ConstantYamlUtil;
import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * ClassName ossMain
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/2
 */

@Api(description="阿里云文件管理")
@ComponentScan(basePackages = {"com.lebrwcd"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableSwagger2
@EnableDiscoveryClient
public class OssMain8002 {

    public static void main(String[] args) {
        SpringApplication.run(OssMain8002.class,args);

        System.out.println(ConstantYamlUtil.endpoint);
    }
}
