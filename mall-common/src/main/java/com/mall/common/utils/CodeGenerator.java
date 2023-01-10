package com.mall.common.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class CodeGenerator {

    public void generator(String outputDir,String packageInfo,String prefix,String table){
        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        //是否支持AR模式
        config.setActiveRecord(true)
                .setAuthor("k0091") //作者
                .setOutputDir(outputDir)  //生成路径
                .setFileOverride(true)//是否文件覆盖，如果多次
                .setServiceName("%sService") //设置生成的service接口名首字母是否为I
                .setIdType(IdType.AUTO) //主键策略
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setDateType(DateType.ONLY_DATE);
        //2. 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)
                .setUrl("jdbc:mysql://localhost:3306/jbos?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL")
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("jbos")
                .setPassword("jbos");
        //3.策略配置
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setCapitalMode(true) // 全局大写命名
                .setNaming(NamingStrategy.underline_to_camel)// 数据库表映射到实体的命名策略
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setInclude(table) //生成的表
                .setTablePrefix(prefix)
                .entityTableFieldAnnotationEnable(true)
                .setControllerMappingHyphenStyle(true)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true);

        //4.包名策略
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent(packageInfo)
        .setEntity("entity");
        //5.整合配置
        AutoGenerator ag = new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig);
        ag.execute();
    }
    public static void main(String[] args) {
        log.info("user.dir={}",System.getProperty("user.dir"));
        String outputDir=System.getProperty("user.dir")+
                File.separator+"mall-common"+
                File.separator+"src"+
                File.separator+"main"+
                File.separator+"java";
        CodeGenerator codeGenerator=new CodeGenerator();
        codeGenerator.generator(outputDir,"com.mall.common","jbos","jbos_user");
    }
}
