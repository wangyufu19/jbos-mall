package com.mall.admin.common.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * DataSourceConfig
 * @author youfu.wang
 * @date 2019-01-31
 */
@Configuration
public class DataSourceConfig {
    @Bean(name = "default")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.default")
    public DataSource defaultDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "camundaBpmDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.camunda")
    public DataSource camundaDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name="camundaBpmTransactionManager")
    public PlatformTransactionManager camundaTransactionManager(@Qualifier("camundaBpmDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
