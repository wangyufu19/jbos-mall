package com.mall.gateway.common.websecurity.filter;

import com.alibaba.nacos.common.utils.JacksonUtils;
import com.mall.common.response.ResponseResult;
import com.mall.gateway.common.config.properties.WebSecurityProperties;
import com.mall.gateway.common.util.WebRequestUtil;
import com.mall.gateway.common.websecurity.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * TokenFilter
 *
 * @author youfu.wang
 * @date 2023/11/3 14:48
 */
@Configuration
@Slf4j
public class TokenFilter implements GlobalFilter {
    @Autowired
    private WebSecurityProperties webSecurityProperties;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        String requestURI = request.getURI().getPath();
        //无需鉴权URI
        if (WebRequestUtil.isExcludeUri(requestURI, webSecurityProperties.getExcludeUri())) {
            return chain.filter(exchange);
        }
        String accessToken = WebRequestUtil.getRequestToken(exchange.getRequest());
        if (!StringUtils.hasLength(accessToken)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(JacksonUtils.toJson(ResponseResult.error("access token is empty！")).getBytes())));
        }
        if (!jwtTokenProvider.verifyToken(accessToken)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(JacksonUtils.toJson(ResponseResult.error("token失效或认证过期！")).getBytes())));
        }
        return chain.filter(exchange);
    }
}
