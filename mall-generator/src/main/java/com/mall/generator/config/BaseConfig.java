package com.mall.generator.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
@Getter
@Builder
public class BaseConfig {
    private String javaOutDir="main"+ File.separator+"java";
    private String resourceOutDir="main"+ File.separator+"resources";
    private String author;
    private String packageInfo;
    private String moduleName;
    private String prefix;
    private String table;

}
