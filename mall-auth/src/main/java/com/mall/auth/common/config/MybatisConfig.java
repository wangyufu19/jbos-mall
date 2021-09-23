package com.mall.auth.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
/**
 * MybatisConfig
 * @author youfu.wang
 * @date 2019-01-31
 */
@Configuration
@MapperScan(basePackages = "com.mall.auth.infrastructure.repository.mapper")
public class MybatisConfig {
}
