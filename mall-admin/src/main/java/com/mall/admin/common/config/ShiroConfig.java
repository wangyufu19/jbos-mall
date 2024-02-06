package com.mall.admin.common.config;

import com.mall.admin.common.shiro.AuthRealm;
import com.mall.admin.common.shiro.AuthTokenFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * ShiroConfig
 *
 * @author youfu.wang
 * @date 2024/2/6 11:14
 */
@Configuration
public class ShiroConfig {
    /**
     * authRealm
     *
     * @return AuthRealm
     */
    @Bean
    public AuthRealm authRealm() {
        return new AuthRealm();
    }

    /**
     * securityManager
     *
     * @param authRealm
     * @return DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(AuthRealm authRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authRealm);
        return securityManager;
    }

    /**
     * shiroFilterFactoryBean
     *
     * @param securityManager
     * @return ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filters = new HashMap<>();
        filters.put("authToken", new AuthTokenFilter());
        shiroFilterFactoryBean.setFilters(filters);
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        filterChainMap.put("/login", "anon");
        filterChainMap.put("/**", "authToken");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }
    /**
     * authorizationAttributeSourceAdvisor
     *
     * @param securityManager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
