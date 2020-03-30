package com.mask.maskdemoserver.config;

import com.mask.maskdemoserver.domains.UserRealm;
import com.mask.maskdemoserver.filter.JWTFilter;
import com.mask.maskdemoserver.service.token.TokenService;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnWebApplication
public class ShiroConfigure {
    private static final String JWT_FILTER_NAME = "jwt";

    /**
     * 自定义realm，实现登录授权流程
     */
    @Bean("userRealm")
    public UserRealm shiroUserRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCachingEnabled(true);
        userRealm.setAuthorizationCachingEnabled(true);
        userRealm.setAuthenticationCachingEnabled(true);
        return userRealm;
    }


    @Bean("securityManager")
    @DependsOn({"userRealm"})
    public DefaultWebSecurityManager securityManager(UserRealm shiroUserRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 缓存管理类 cacheManager，这个cacheManager必须要在前面执行，因为setRealm 和 setSessionManage都有方法初始化了cachemanager,看下源码就知道了
//        securityManager.setCacheManager(cacheManager(iCacheService, jwtProperties));

        securityManager.setRealm(shiroUserRealm);
        // 关闭自带session
        DefaultSessionStorageEvaluator evaluator = new DefaultSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(false);

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(evaluator);

        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    @Bean
    public FilterRegistrationBean delegatingFilterProxy() {
        FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    public JWTFilter jwtFilterBean(TokenService tokenService) {
        return new JWTFilter(tokenService);
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager, TokenService tokenService) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setFilters(filterMap(jwtFilterBean(tokenService)));
        factoryBean.setFilterChainDefinitionMap(definitionMap());
        return factoryBean;
    }


    /**
     * 自定义拦截器，处理所有请求
     */
    private Map<String, Filter> filterMap(JWTFilter jwtFilter) {
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put(JWT_FILTER_NAME, jwtFilter);
        return filterMap;
    }

    /**
     * url拦截规则
     */
    private Map<String, String> definitionMap() {
        Map<String, String> definitionMap = new HashMap<>();
        // 配置
        definitionMap.put("/websocket/**", "anon");
        definitionMap.put("/druid/**", "anon");
        definitionMap.put("/**", JWT_FILTER_NAME);
        // 设置 swagger-bootstrap-ui 不被拦截
        definitionMap.put("/swagger-resources", "anon");
        definitionMap.put("/v2/api-docs", "anon");
        definitionMap.put("/v2/api-docs-ext", "anon");
        definitionMap.put("/webjars/**", "anon");
        definitionMap.put("/doc.html", "anon");

        // 接口模块
        definitionMap.put("/captcha/**", "anon");
        definitionMap.put("/user/**", "anon");
        return definitionMap;
    }

    /**
     * 开启注解
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 管理生命周期
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 注解访问授权动态拦截，不然不会执行doGetAuthenticationInfo
     *
     * @param securityManager 安全管理器
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}