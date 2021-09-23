package com.mall.auth.common.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
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
public class DataSourceConfig {
    @Bean(name = "default")
    @ConfigurationProperties(prefix = "spring.datasource.druid.default")
    public DataSource defaultDataSource(){
        return DruidDataSourceBuilder.create().build();
    }
}
