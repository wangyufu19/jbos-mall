package com.mall.admin.common.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * DataSourceConfig
 * @author youfu.wang
 * @date 2019-01-31
 */
@Configuration
@MapperScan(basePackages = "com.mall.admin.infrastructure.repository.mapper")
public class DataSourceConfig {
    @Bean(name = "default")
    @ConfigurationProperties(prefix = "spring.datasource.druid.default")
    public DataSource defaultDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

//    @Bean(name = "activiti")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.activiti")
//    public DataSource activitiDataSource(){
//        return DruidDataSourceBuilder.create().build();
//    }
}
