package com.mall.generator.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DataSourceConfig {
    private String url;
    private String username;
    private String password;
}
