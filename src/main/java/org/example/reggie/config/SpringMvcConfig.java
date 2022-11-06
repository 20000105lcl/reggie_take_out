package org.example.reggie.config;

import org.example.reggie.common.JacksonObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @Title: SpringMvcConfig
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/10/30 16:43
 * @description:
 */
@Configuration
public class SpringMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    /**
     * 扩展springmvc框架的消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        //创建一个消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器的转换规则
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将消息转换器加入到消息转换器集合中，索引指加入到什么位置，最终消息转换器生效是按照其集合中的顺序。
        converters.add(0,messageConverter);

    }
}
