package com.panghu.blog.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * Jackson配置
 * @author William
 *
 */
@Configuration
public class JacksonConfig {

    /**
     * Jackson全局转化long类型为String，解决jackson序列化时long类型缺失精度问题
     * @return Jackson2ObjectMapperBuilderCustomizer 注入的对象
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        Jackson2ObjectMapperBuilderCustomizer cunstomizer = new Jackson2ObjectMapperBuilderCustomizer() {

            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {

                jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            }
        };

        return cunstomizer;
    }
}