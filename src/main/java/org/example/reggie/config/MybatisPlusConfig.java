package org.example.reggie.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: MybatisPlusConfig
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/10/31 21:10
 * @description: MybatisPlus配置，分页
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * MybatisPlus分页拦截器
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mpi = new MybatisPlusInterceptor();
        mpi.addInnerInterceptor(new PaginationInnerInterceptor());
        return mpi;
    }

}
