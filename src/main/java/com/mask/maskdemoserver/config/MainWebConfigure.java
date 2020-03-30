package com.mask.maskdemoserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-14 09:27
 * @Description:
 */
@Configuration
public class MainWebConfigure extends WebMvcConfigurationSupport {

    /**
     * spring-boot资源默认配置路径
     */
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { //
            "classpath:/META-INF/resources/", //
            "classpath:/resources/", //
            "classpath:/static/", //
            "classpath:/public/" //
    };
    /**
     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。 需要重新指定静态资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    /**
     * 跨域设置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //
                .allowedOrigins("*") //
                .allowCredentials(true) //
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS") //
                .allowedHeaders("origin", "content-type", "accept", "authorization", "x-requested-with", "x-authorization", "token") //
                .maxAge(3600);
    }
}
