package com.mall.gateway.common.filter;

import com.alibaba.nacos.common.utils.JacksonUtils;
import com.mall.gateway.common.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.File;

/**
 * TokenGlobalFilter
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
@Slf4j
public class TokenFilter implements GlobalFilter, Ordered {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Value("${spring.cloud.gateway.excludeUri}")
    private String excludeUri;
    private AntPathMatcher antPathMatcher=new AntPathMatcher(File.separator);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request=exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add("Content-Type","application/json");
        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
        String requestURI=request.getURI().getPath();
        log.info("send {} request to {}", request.getMethodValue(),requestURI);
        //过滤URI白名单
        String[] excludeUris=excludeUri.split(",");
        for(String uri:excludeUris){
            if(antPathMatcher.match(uri,requestURI)){
                return chain.filter(exchange);
            }
        }
        String accessToken = this.getRequestToken(request);
        if(accessToken == null) {
            log.error("access token is empty");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            String json=JacksonUtils.toJson(ResponseData.error("access token is empty"));
            return response.writeWith(Mono.just(response.bufferFactory().wrap(json.getBytes())));
        }
        if (!jwtTokenProvider.verifyToken(accessToken)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(JacksonUtils.toJson(ResponseData.error("token失效或认证过期！")).getBytes())));
        }
        RequestDecorator requestDecorator=new RequestDecorator(request);
        ResponseDecorator responseDecorator = new ResponseDecorator(response);
        return chain.filter(exchange.mutate().request(requestDecorator).response(responseDecorator).build());
    }

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER-1;
    }

    private String getRequestToken(ServerHttpRequest request) {
        String accessToken = request.getHeaders().getFirst("accessToken");
        if (accessToken == null) {
            return request.getQueryParams().getFirst("accessToken");
        } else {
            return accessToken;
        }
    }
    public class RequestDecorator extends ServerHttpRequestDecorator{

        public RequestDecorator(ServerHttpRequest delegate) {
            super(delegate);
        }
        public Flux<DataBuffer> getBody() {
            Flux<DataBuffer> body=super.getBody();
            log.info("******body={}",body);
            return body;
        }

    }
    public class ResponseDecorator extends ServerHttpResponseDecorator{

        public ResponseDecorator(ServerHttpResponse delegate) {
            super(delegate);
        }
        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body){
            log.info("******body={}",body);
            if (body instanceof Flux) {
                Flux<? extends DataBuffer> flux = (Flux<? extends DataBuffer>) body;
                log.info("******flux={}",flux);
            }
            return super.writeWith(body);
        }
        public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
            return this.getDelegate().writeAndFlushWith(body);
        }
    }
}
