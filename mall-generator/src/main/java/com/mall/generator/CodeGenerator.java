package com.mall.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class CodeGenerator {

    public void generator(String author,String outputDir,String packageInfo,String prefix,String table){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/jbos?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL", "jbos", "jbos")
                .globalConfig(builder -> {
                    builder.author(author) //作者
                           .fileOverride() // 覆盖已生成文件
                           .disableOpenDir()
                           .outputDir(outputDir); //输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(packageInfo); // 设置父包名
                })
                .templateConfig(builder -> {
                    builder.build();
                })
                .strategyConfig(builder -> {
                    builder.addInclude(table)//设置需要生成的表名
                           .addTablePrefix(prefix)//设置过滤表前缀
                           .entityBuilder()
                           .enableLombok()//允许启用lombok
                           .enableTableFieldAnnotation()// 开启生成实体时,生成字段注解 即 @TableField
                           .idType(IdType.AUTO);//主键生成策略;
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
    public static void main(String[] args) {
        log.info("user.dir={}",System.getProperty("user.dir"));
        String outputDir=System.getProperty("user.dir")+
                File.separator+"mall-generator"+
                File.separator+"src"+
                File.separator+"main"+
                File.separator+"java";
        CodeGenerator codeGenerator=new CodeGenerator();
        codeGenerator.generator("k0091",outputDir,"com.mall.application","jbos","jbos_user");
    }
}
