package com.mall.gateway.common.config;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableOpenApi
@Primary
public class SwaggerConfig implements SwaggerResourcesProvider {
    /**
     * applicationName
     */
    @Value("${spring.application.name:}")
    private String applicationName;
    /**
     * spring-cloud-gateway是否开启了根据服务发现自动为服务创建router
     */
    @Value("${spring.cloud.gateway.discovery.locator.enabled:false}")
    private boolean autoCreateRouter;

    /**
     * 是否开启swagger
     */
    @Value(value = "${swagger.enabled}")
    private Boolean swaggerEnabled;
    /**
     * routeLocator
     */
    @Resource
    private RouteLocator routeLocator;
    /**
     * gatewayProperties
     */
    @Resource
    private GatewayProperties gatewayProperties;

    /**
     * createRestApi
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).enable(true)
                .enable(swaggerEnabled)
                .select()
                //加了ApiOperation注解的类，才生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
                //.apis(RequestHandlerSelectors.basePackage("com.mall.admin.application")
                //代表所有路径
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(Collections.singletonList(securityContext()));
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
        List<SecurityScheme> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey("Authorization", "accessToken", "header"));
        return apiKeyList;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("Authorization", scopes())))
                .build();
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope("web", "All scope is trusted!")
        };

    }

    /**
     * 根据当前所有的微服务路由信息，创建对应的SwaggerResource
     */
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> finalResources;
        Set<String> routes = new LinkedHashSet<>(16);
        // 获取所有路由的id
        routeLocator.getRoutes().subscribe(route -> {
            String routeId = route.getId();
            routeId = routeId.replace("ReactiveCompositeDiscoveryClient_", "");
            routes.add(routeId);
        });
        // 没有开启自动创建路由，那么走配置文件中配置的路由
        if (!autoCreateRouter) {
            finalResources = new ArrayList<>(16);
            gatewayProperties.getRoutes().stream()
                    // 过滤出配置文件中定义的路由
                    .filter(routeDefinition -> routes.contains(routeDefinition.getId())).forEach(route -> {
                        route.getPredicates().stream()
                                // 过滤出设置有Path Predicate的路由
                                .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                                // 根据路径拼接成api-docs路径,生成SwaggerResource
                                .forEach(predicateDefinition -> finalResources.add(swaggerResource(route.getId(),
                                        predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                                // 如果对应的微服务设置了server.servlet.context-path，那么这里应该是{context-path}/v3/api-docs
                                                .replace("**", "v3/api-docs"))));
                    });
        } else {
            // 如果对应的微服务设置了server.servlet.context-path，那么这里应该是/{context-path}/v3/api-docs
            finalResources = routes.stream().map(routeId -> swaggerResource(routeId, routeId + "/v3/api-docs")).collect(Collectors.toList());
        }
        List<SwaggerResource> resources = new ArrayList<>(finalResources);

        // resources过滤掉网关的SwaggerResource, 我们一般也不会在网关中编写业务controller
//        if (StringUtils.isNotBlank(applicationName)) {
//            resources = resources.stream().filter(x -> !applicationName.equalsIgnoreCase(x.getName())).collect(Collectors.toList());
//        }
        // 排序
        resources.sort(Comparator.comparing(x -> x.getName().length()));
        return resources;
    }

    /**
     * swaggerResource
     *
     * @param name
     * @param location
     * @return
     */
    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location + "?group=" + name);
        swaggerResource.setSwaggerVersion("3.0");
        return swaggerResource;
    }
}