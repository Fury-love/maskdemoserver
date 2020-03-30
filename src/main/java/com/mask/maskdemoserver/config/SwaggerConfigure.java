package com.mask.maskdemoserver.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-14 10:52
 * @Description:
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfigure {
    /**
     * 后台管理
     */
    @Bean
    public Docket innerApi() {
        return new Docket(DocumentationType.SWAGGER_2) //
                .apiInfo(innerApiInfo()) //
                .groupName("后台管理") //
                .genericModelSubstitutes(DeferredResult.class) //
                .useDefaultResponseMessages(false) //
                .forCodeGeneration(true) //
                .select() //
                .apis(RequestHandlerSelectors.basePackage("com.mask.maskdemoserver.controller")) //
                .paths(PathSelectors.any()) //
                .build(); //
    }

    private ApiInfo innerApiInfo() {
        return new ApiInfoBuilder(). //
                title("maskdemo APIs")// 大标题
                .description("后台APIs")// 详细描述
                .version("1.0")// 版本
                .termsOfServiceUrl("NO terms of service") //
                .build();
    }
}
