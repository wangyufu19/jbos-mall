package com.mall.gateway.application;

import com.alibaba.nacos.common.utils.JacksonUtils;
import com.mall.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * GatewayFallbackApi
 */
@RestController
@Slf4j
public class GatewayFallbackApi {
    /**
     * fallback
     * @param exchange
     * @param throwable
     * @return
     */
    @RequestMapping(value = "/fallback")
    public Mono<Void> fallback(ServerWebExchange exchange, Throwable throwable) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        ResponseResult r = ResponseResult.error(throwable.getMessage());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("serviceId", route.getId());
        r.setData(data);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(JacksonUtils.toJson(r).getBytes(Charset.forName("UTF-8")))));
    }
}
