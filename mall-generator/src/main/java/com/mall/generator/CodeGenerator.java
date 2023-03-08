package com.mall.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.mall.generator.config.BaseConfig;
import com.mall.generator.config.DataSourceConfig;
import com.mall.generator.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
public class CodeGenerator {
    public static final String projectJavaOutDir="main"+ File.separator+"java";
    public static final String projectResourceOutDir="main"+ File.separator+"resources";
    private String outputDir;
    private BaseConfig.BaseConfigBuilder baseConfigBuilder;
    private DataSourceConfig.DataSourceConfigBuilder dataSourceConfigBuilder;

    public CodeGenerator(){
        this.baseConfigBuilder=BaseConfig.builder();
        this.dataSourceConfigBuilder=DataSourceConfig.builder();

    }
    public static CodeGenerator getInstance(){
        return new CodeGenerator();
    }

    public CodeGenerator outputDir(String outputDir){
        this.outputDir=outputDir;
        if(outputDir.endsWith(File.separator)){
            this.baseConfigBuilder.javaOutDir(outputDir+projectJavaOutDir);
        }else{
            this.baseConfigBuilder.javaOutDir(outputDir+File.separator+projectJavaOutDir);
        }
        if(projectResourceOutDir.endsWith(File.separator)){
            this.baseConfigBuilder.resourceOutDir(outputDir+projectResourceOutDir);
        }else{
            this.baseConfigBuilder.resourceOutDir(outputDir+File.separator+projectResourceOutDir);
        }
        return this;
    }
    public CodeGenerator baseConfig(Consumer<BaseConfig.BaseConfigBuilder> consumer) {
        consumer.accept(this.baseConfigBuilder);
        return this;
    }
    public CodeGenerator dataSourceConfig(Consumer<DataSourceConfig.DataSourceConfigBuilder> consumer) {
        consumer.accept(this.dataSourceConfigBuilder);
        return this;
    }

    private void generator(DataSourceConfig dataSourceConfig,BaseConfig baseConfig) {
        FastAutoGenerator.create(dataSourceConfig.getUrl(),dataSourceConfig.getUsername(),dataSourceConfig.getPassword())
                .globalConfig(builder -> {
                    builder.author(baseConfig.getAuthor()) //作者
                           .fileOverride() // 覆盖已生成文件
                           .disableOpenDir()
                           .outputDir(baseConfig.getJavaOutDir()); //输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(baseConfig.getPackageInfo()) // 设置父包名
                           .moduleName(baseConfig.getModuleName()) //设置父包模块名
                           .pathInfo(Collections.singletonMap(OutputFile.mapperXml, baseConfig.getResourceOutDir()));
                })
                .templateConfig(builder -> {
                    builder.build();
                })
                .strategyConfig(builder -> {
                    builder.addInclude(baseConfig.getTable())//设置需要生成的表名
                           .addTablePrefix(baseConfig.getPrefix())//设置过滤表前缀
                           .entityBuilder()
                           .enableLombok()//允许启用lombok
                           .enableTableFieldAnnotation()// 开启生成实体时,生成字段注解 即 @TableField
                           .idType(IdType.AUTO);//主键生成策略;
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }
    public void generatorCli() throws Exception {
        DataSourceConfig dataSourceConfig=this.dataSourceConfigBuilder.build();
        BaseConfig baseConfig=this.baseConfigBuilder.build();
        //生成controller,service,entity,mapper
        this.generator(dataSourceConfig,baseConfig);

        Map<String, Object> objectMap=new HashMap<>();
        objectMap.put("springApplicationName","mall-generator");
        //生成Yml文件
        File ymlFile=new File(baseConfig.getResourceOutDir()+File.separator+"ftl"+File.separator+"application.yml");
        this.outputFile(objectMap,"ftl/application.yml.ftl",ymlFile);
        String javaOutDir=baseConfig.getJavaOutDir();
        String packageInfo=baseConfig.getPackageInfo();
        String table=baseConfig.getTable();
        String prefix=baseConfig.getPrefix();
        String packagePath=baseConfig.getPackageInfo().replace(".",File.separator);
        String entityName= StringUtil.getUpperCamelCase(table.substring(table.indexOf(prefix)+prefix.length()+1));

        //生成infrastructure,repository,mapper
        objectMap.put("author",baseConfig.getAuthor());
        objectMap.put("date",new Date().toString());
        String infrastructure=javaOutDir+File.separator+packagePath+File.separator+"infrastructure";
        if(!new File(infrastructure).exists()){
            new File(infrastructure).mkdirs();
        }
        //生成Repo文件
        String repository=javaOutDir+File.separator+packagePath+File.separator+"infrastructure"+File.separator+"repository";
        if(!new File(repository).exists()){
            new File(repository).mkdir();
        }
        File repoFile=new File(repository+File.separator+entityName+"Repo.java");
        objectMap.put("packageName",packageInfo+"."+"infrastructure"+"."+"repository");
        objectMap.put("comment",entityName+"Repo");
        objectMap.put("repo",entityName+"Repo");
        this.outputFile(objectMap,"ftl/repo.java.ftl",repoFile);
        //生成Repo文件
        String mapper=javaOutDir+File.separator+packagePath+File.separator+"infrastructure"+File.separator+"repository"+File.separator+"mapper";
        File mapperFile=new File(mapper+File.separator+entityName+"Mapper.java");
        if(!new File(mapper).exists()){
            new File(mapper).mkdir();
        }
        objectMap.put("packageName",packageInfo+"."+"infrastructure"+"."+"repository"+"."+"mapper");
        objectMap.put("comment",entityName+"Mapper");
        objectMap.put("mapper",entityName+"Mapper");
        this.outputFile(objectMap,"ftl/mapper.java.ftl",mapperFile);
    }
    private void outputFile(Map<String, Object> objectMap,String templatePath,File outputFile) throws Exception {
        AbstractTemplateEngine templateEngine=new FreemarkerTemplateEngine();
        templateEngine.init(null);
        templateEngine.writer(objectMap,templatePath,outputFile);
    }
    public static void main(String[] args) throws Exception {
        log.info("user.dir={}",System.getProperty("user.dir"));
        String outputDir=System.getProperty("user.dir")+
                File.separator+"mall-generator"+
                File.separator+"src";
        CodeGenerator.getInstance()
                .outputDir(outputDir)
                .dataSourceConfig(builder -> {
                    builder.url("jdbc:mysql://localhost:3306/jbos?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL")
                           .username("jbos")
                           .password("jbos");
                })
                .baseConfig(builder -> {
                    builder.author("k0091")
                           .packageInfo("com.mall.cg")
                           .moduleName(null)
                           .prefix("jbos")
                           .table("jbos_user") ;
                })
                .generatorCli();
    }
}
