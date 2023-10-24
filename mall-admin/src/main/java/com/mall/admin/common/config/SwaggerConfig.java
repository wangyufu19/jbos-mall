package com.mall.admin.common.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import javax.annotation.Nonnull;
import javax.print.Doc;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableOpenApi
public class SwaggerConfig{
    @Autowired
    private Environment environment;
    @Bean
    public Docket createRestApi() {
        Docket docket= new Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo()).enable(true)
            .select()
            //加了ApiOperation注解的类，才生成接口文档
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            //包下的类，才生成接口文档
            //.apis(RequestHandlerSelectors.basePackage("com.mall.admin.application")
            //代表所有路径
            .paths(PathSelectors.any())
            .build().securitySchemes(securitySchemes());
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("jbos-mall")
            .description("jbos-mall api")
            .termsOfServiceUrl("https://www.jbos-mall.cn")
            .version("v1.0")
            .build();
    }
    private List<SecurityScheme> securitySchemes() {
        return newArrayList(
            new ApiKey("token", "token", "header")
        );
    }
    /**
     * Spring Boot 2.6 与 swagger 不兼容解决
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(WebMvcRequestHandlerProvider.class)
    public static class FixNpeForSpringfoxHandlerProviderBeanPostProcessorConfiguration {

        @Bean
        public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
            return new BeanPostProcessor() {

                @Override
                public Object postProcessAfterInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
                    if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                        customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                    }
                    return bean;
                }

                private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                    List<T> copy = mappings.stream()
                            .filter(mapping -> mapping.getPatternParser() == null)
                            .collect(Collectors.toList());
                    mappings.clear();
                    mappings.addAll(copy);
                }

                @SuppressWarnings("unchecked")
                private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                    try {
                        Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                        field.setAccessible(true);
                        return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                }
            };
        }
    }

}