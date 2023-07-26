package com.mall.generator.config;

import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TemplateConfig {
    public static final String projectJavaOutDir="main"+ File.separator+"java";
    public static final String projectResourceOutDir="main"+ File.separator+"resources";
    public static final String TLT_YML="application.yml";
    public static final String TLT_JAVA=".java";
    public static final String TLT_SPRING_APPLICATION_NAME="springApplicationName";
    public static final String TLT_PACKAGE_NAME="packageName";
    public static final String TLT_COMMENT="comment";
    public static final String TLT_AUTHOR="author";
    public static final String TLT_DATE="date";
    public static final String TLT_INFRASTRUCTURE="infrastructure";
    public static final String TLT_REPOSITORY="repository";
    public static final String TLT_MAPPER="mapper";
    public static final String TLT_ENTITY="entity";
    public static final String TLT_ENTITY_OPERATORS="operators";
    private List<EntityOperator> entityOperators=new ArrayList<>();

    private String yml;
    private String repo;
    private String mapper;

    public TemplateConfig(String yml,String repo,String mapper){
        this.yml=yml;
        this.repo=repo;
        this.mapper=mapper;
        this.entityOperators.add(EntityOperator.get);
        this.entityOperators.add(EntityOperator.list);
        this.entityOperators.add(EntityOperator.add);
        this.entityOperators.add(EntityOperator.update);
        this.entityOperators.add(EntityOperator.delete);
    }
    public static TemplateConfig.TemplateConfigBuilder builder() {
        return new TemplateConfig.TemplateConfigBuilder();
    }

    public void entityOperator(EntityOperator entityOperator){
        this.entityOperators.add(entityOperator);
    }
    public void setEntityOperator(List<EntityOperator> entityOperators){
        this.entityOperators=entityOperators;
    }
    public List<EntityOperator> getEntityOperators(){
        return this.entityOperators;
    }
    public static class TemplateConfigBuilder {
        private String yml;
        private String repo;
        private String mapper;

        TemplateConfigBuilder() {
            this.yml="ftl/application.yml.ftl";
            this.repo="ftl/repo.java.ftl";
            this.mapper="ftl/mapper.java.ftl";
        }

        public TemplateConfig.TemplateConfigBuilder yml(final String yml) {
            this.yml = yml;
            return this;
        }

        public TemplateConfig.TemplateConfigBuilder repo(final String repo) {
            this.repo = repo;
            return this;
        }

        public TemplateConfig.TemplateConfigBuilder mapper(final String mapper) {
            this.mapper = mapper;
            return this;
        }

        public TemplateConfig build() {
            return new TemplateConfig(this.yml, this.repo, this.mapper);
        }
        public String toString() {
            return "TemplateConfig.TemplateConfigBuilder(yml=" + this.yml + ", repo=" + this.repo + ", mapper=" + this.mapper + ")";
        }
    }
}
