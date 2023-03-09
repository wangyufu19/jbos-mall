package com.mall.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.mall.generator.config.BaseConfig;
import com.mall.generator.config.DataSourceConfig;
import com.mall.generator.config.TemplateConfig;
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

    private String outputDir;
    private BaseConfig.BaseConfigBuilder baseConfigBuilder;
    private DataSourceConfig.DataSourceConfigBuilder dataSourceConfigBuilder;
    private TemplateConfig.TemplateConfigBuilder templateConfigBuilder;

    public CodeGenerator(){
        this.baseConfigBuilder=BaseConfig.builder();
        this.dataSourceConfigBuilder=DataSourceConfig.builder();
        this.templateConfigBuilder=TemplateConfig.builder();
    }
    public static CodeGenerator getInstance(){
        return new CodeGenerator();
    }

    public CodeGenerator outputDir(String outputDir){
        this.outputDir=outputDir;
        if(outputDir.endsWith(File.separator)){
            this.baseConfigBuilder.javaOutDir(outputDir+TemplateConfig.projectJavaOutDir);
        }else{
            this.baseConfigBuilder.javaOutDir(outputDir+File.separator+TemplateConfig.projectJavaOutDir);
        }
        if(TemplateConfig.projectResourceOutDir.endsWith(File.separator)){
            this.baseConfigBuilder.resourceOutDir(outputDir+TemplateConfig.projectResourceOutDir);
        }else{
            this.baseConfigBuilder.resourceOutDir(outputDir+File.separator+TemplateConfig.projectResourceOutDir);
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
    public CodeGenerator templateConfig(Consumer<TemplateConfig.TemplateConfigBuilder> consumer) {
        consumer.accept(this.templateConfigBuilder);
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
    public void generatorCli(String springApplicationName) throws Exception {
        DataSourceConfig dataSourceConfig=this.dataSourceConfigBuilder.build();
        BaseConfig baseConfig=this.baseConfigBuilder.build();
        TemplateConfig templateConfig=this.templateConfigBuilder.build();
        //生成controller,service,entity,mapper
        this.generator(dataSourceConfig,baseConfig);

        Map<String, Object> objectMap=new HashMap<>();
        objectMap.put(TemplateConfig.TLT_SPRING_APPLICATION_NAME,springApplicationName);
        //生成Yml文件
        this.outputFileYml(objectMap,templateConfig.getYml(),baseConfig.getResourceOutDir());

        String javaOutDir=baseConfig.getJavaOutDir();
        String packageInfo=baseConfig.getPackageInfo();
        String packagePath=packageInfo.replace(".",File.separator);
        String table=baseConfig.getTable();
        String prefix=baseConfig.getPrefix();
        String entityName=StringUtil.getUpperCamelCase(table.substring(table.indexOf(prefix)+prefix.length()+1));

        //生成infrastructure,repository,mapper
        objectMap.put(TemplateConfig.TLT_AUTHOR,baseConfig.getAuthor());
        objectMap.put(TemplateConfig.TLT_DATE,new Date().toString());
        String infrastructure=javaOutDir+File.separator+packagePath+File.separator+TemplateConfig.TLT_INFRASTRUCTURE;
        if(!new File(infrastructure).exists()){
            new File(infrastructure).mkdirs();
        }
        //生成Repo文件
        this.outputFileRepo(objectMap,templateConfig.getRepo(),javaOutDir,packageInfo,packagePath,entityName);
        //生成Mapper文件
        this.outputFileMapper(objectMap,templateConfig.getMapper(),javaOutDir,packageInfo,packagePath,entityName);
    }
    private void outputFileYml(Map<String, Object> objectMap,String templatePath,String resourceOutDir) throws Exception {
        File ymlFile=new File(resourceOutDir+File.separator+TemplateConfig.TLT_YML);
        this.outputFile(objectMap,templatePath,ymlFile);
    }
    private void outputFileRepo(Map<String, Object> objectMap,String templatePath,String javaOutDir,String packageInfo,String packagePath,String entityName) throws Exception {
        String repository=javaOutDir+File.separator+packagePath+File.separator+TemplateConfig.TLT_INFRASTRUCTURE+File.separator+TemplateConfig.TLT_REPOSITORY;
        if(!new File(repository).exists()){
            new File(repository).mkdir();
        }
        String objectName=entityName+StringUtil.getUpperCamelCase(TemplateConfig.TLT_REPOSITORY);
        File repoFile=new File(repository+File.separator+objectName+TemplateConfig.TLT_JAVA);
        objectMap.put(TemplateConfig.TLT_PACKAGE_NAME,packageInfo+"."+TemplateConfig.TLT_INFRASTRUCTURE+"."+TemplateConfig.TLT_REPOSITORY);
        objectMap.put(TemplateConfig.TLT_COMMENT,objectName);
        objectMap.put(TemplateConfig.TLT_REPOSITORY,objectName);
        this.outputFile(objectMap,templatePath,repoFile);
    }
    private void outputFileMapper(Map<String, Object> objectMap,String templatePath,String javaOutDir,String packageInfo,String packagePath,String entityName) throws Exception {
        String mapper=javaOutDir+File.separator+packagePath+File.separator+TemplateConfig.TLT_INFRASTRUCTURE+File.separator+TemplateConfig.TLT_REPOSITORY+File.separator+TemplateConfig.TLT_MAPPER;
        if(!new File(mapper).exists()){
            new File(mapper).mkdir();
        }
        String objectName=entityName+StringUtil.getUpperCamelCase(TemplateConfig.TLT_MAPPER);
        File mapperFile=new File(mapper+File.separator+objectName+TemplateConfig.TLT_JAVA);
        objectMap.put(TemplateConfig.TLT_PACKAGE_NAME,packageInfo+"."+TemplateConfig.TLT_INFRASTRUCTURE+"."+TemplateConfig.TLT_REPOSITORY+"."+TemplateConfig.TLT_MAPPER);
        objectMap.put(TemplateConfig.TLT_COMMENT,objectName);
        objectMap.put(TemplateConfig.TLT_MAPPER,objectName);
        this.outputFile(objectMap,templatePath,mapperFile);
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
                .generatorCli("mall-generator");
    }
}
