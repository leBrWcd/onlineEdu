package com.lebrwcd.eduservice.config;/**
 * @author lebrwcd
 * @date 2022/4/24
 * @note
 */

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ClassName MapperConf
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/4/24
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.lebrwcd.eduservice.mapper")
public class EduConf {

    /**
     * 逻辑删除需要的bean
     * @return
     */
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }

    /**
     * mybatis-plus分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
