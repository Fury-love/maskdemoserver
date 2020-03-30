package com.mask.maskdemoserver.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-23 11:53
 * @Description:
 */
@Configuration
public class MybatisPlusConfigure {
    /**
     * 自动填充功能
     * @return
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MetaHandler());
        return globalConfig;
    }
}
