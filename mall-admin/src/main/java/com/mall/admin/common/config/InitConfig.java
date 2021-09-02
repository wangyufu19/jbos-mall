package com.mall.admin.common.config;

import com.mall.admin.application.service.BusinessDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * InitConfig
 * @author youfu.wang
 * @date 2020-07-23
 */
@Configuration
@Slf4j
public class InitConfig {
    /**
     * 初始化业务字典
     * @param transactionManager
     * @return
     */
    @Bean
    public BusinessDict intBusinessDict(DataSourceTransactionManager transactionManager, @Qualifier("default") DataSource dataSource){
        BusinessDict businessDict=new BusinessDict();
        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
        businessDict.setJdbcTemplate(jdbcTemplate);
        businessDict.init();
        return businessDict;
    }
}
