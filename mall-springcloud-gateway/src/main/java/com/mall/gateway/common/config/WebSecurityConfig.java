package com.mall.gateway.common.config;


import com.alibaba.nacos.common.utils.JacksonUtils;
import com.mall.common.response.ResponseResult;
import com.mall.gateway.common.config.properties.WebSecurityProperties;
import com.mall.gateway.common.websecurity.TokenAuthenticationManager;
import com.mall.gateway.common.websecurity.jwt.JwtTokenProvider;
import com.mall.gateway.common.websecurity.user.JwtUser;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * WebSecurityConfig
 *
 * @author youfu.wang
 * @date 2021-05-21
 */
@Slf4j
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
@EnableConfigurationProperties({WebSecurityProperties.class})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ReactiveUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private WebSecurityProperties webSecurityProperties;
    @Autowired
    private TokenAuthenticationManager tokenAuthenticationManager;
    /**
     * antPathMatcher
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher(File.separator);

    /**
     * getExcludeUris
     *
     * @return excludeUris
     */
    private String[] getExcludeUris() {
        String excludeUri = webSecurityProperties.getExcludeUri();
        String[] excludeUris = excludeUri.split(",");
        return excludeUris;
    }

    private boolean isExcludeUri(String requestURI) {
        String[] excludeUris = getExcludeUris();
        boolean matches = false;
        for (String uri : excludeUris) {
            matches = antPathMatcher.match(uri, requestURI);
            if (matches) {
                break;
            }
        }
        return matches;
    }

    private String getRequestToken(ServerHttpRequest request) {

        String accessToken = request.getHeaders().getFirst(JwtTokenProvider.TOKEN);
        if (accessToken == null) {
            return request.getQueryParams().getFirst(JwtTokenProvider.TOKEN);
        } else {
            return accessToken;
        }
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity
                // 登录认证处理
                .authenticationManager(reactiveAuthenticationManager())
                .securityContextRepository(new DefaultSecurityContextRepository())
                .addFilterAfter(new JwtWebFilter(), SecurityWebFiltersOrder.FIRST)
                // 请求拦截处理
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(this.getExcludeUris()).permitAll()
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyExchange().authenticated()
                )
                // 登录URI及成功和失败处理器
                .formLogin().loginPage(webSecurityProperties.getLoginUri())
                .authenticationSuccessHandler(new DefaultAuthenticationSuccessHandler())
                .authenticationFailureHandler(new DefaultAuthenticationFailureHandler())
                .and()
                // 登出URI及登出成功处理器
                .logout().logoutUrl(webSecurityProperties.getLogoutUri())
                .logoutHandler(new DefaultServerLogoutHandler()).logoutSuccessHandler(new DefaultServerLogoutSuccessHandler())
                .and()
                // 异常处理
                .exceptionHandling()
                .authenticationEntryPoint((exchange, ex) -> {
                    ServerHttpRequest request = exchange.getRequest();
                    String requestURI = request.getURI().getPath();
                    //请求认证异常
                    ServerHttpResponse response = exchange.getResponse();
                    response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    return response.writeWith(Mono.just(response.bufferFactory().wrap(JacksonUtils.toJson(ResponseResult.error("对不起，非法请求！")).getBytes())));
                })
                .accessDeniedHandler((exchange, ex) -> {
                    ServerHttpRequest request = exchange.getRequest();
                    String requestURI = request.getURI().getPath();
                    //请求权限异常
                    ServerHttpResponse response = exchange.getResponse();
                    response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    return response.writeWith(Mono.just(response.bufferFactory().wrap(JacksonUtils.toJson(ResponseResult.error("对不起，没有权限！")).getBytes())));
                })
                .and()
                .csrf().disable()  //禁用csrf
                .cors(); //启用跨域请求
        return httpSecurity.build();
    }

    /**
     * BCrypt密码编码
     */
    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        LinkedList<ReactiveAuthenticationManager> managers = new LinkedList<>();
        managers.add(authentication -> {
            // 其他登陆方式 (比如手机号验证码登陆) 可在此设置不得抛出异常或者 Mono.error
            return Mono.empty();
        });
        // 必须放最后不然会优先使用用户名密码校验但是用户名密码不对时此 AuthenticationManager 会调用 Mono.error 造成后面的 AuthenticationManager 不生效
        managers.add(new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService));
        return new DelegatingReactiveAuthenticationManager(managers);
    }

    public class DefaultSecurityContextRepository implements ServerSecurityContextRepository {
        @Override
        public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
            return Mono.empty();
        }

        @Override
        public Mono<SecurityContext> load(ServerWebExchange exchange) {
            ServerHttpRequest request = exchange.getRequest();
            String requestURI = request.getURI().getPath();
            //无需鉴权URI
            if (isExcludeUri(requestURI)) {
                return Mono.empty();
            }
            String accessToken = getRequestToken(request);
            String username = jwtTokenProvider.getSignDataFromJWT(accessToken, "username");
            String nickName = jwtTokenProvider.getSignDataFromJWT(accessToken, "nickName");
            List<GrantedAuthority> grantedAuthorities = jwtTokenProvider.getGrantedAuthorityFromJWT(accessToken, JwtUser.AUTHORITIES);
            UserDetails userDetails = new JwtUser(username, nickName, grantedAuthorities);
            // 构建认证过的token
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            if (StringUtils.hasLength(accessToken)) {
                return tokenAuthenticationManager.authenticate(authentication
                ).map(SecurityContextImpl::new);
            }
            return Mono.empty();
        }
    }

    public class JwtWebFilter implements WebFilter {
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            String requestURI = request.getURI().getPath();
            //无需鉴权URI
            if (isExcludeUri(requestURI)) {
                return chain.filter(exchange);
            }
            String accessToken = getRequestToken(exchange.getRequest());
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

    public class DefaultAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {
        /**
         * onAuthenticationSuccess
         *
         * @param webFilterExchange
         * @param authentication
         * @return
         */
        public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
            ServerWebExchange exchange = webFilterExchange.getExchange();
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            //获取用户对象
            JwtUser principal = (JwtUser) authentication.getPrincipal(); // 获取用户对象
            Map<String, String> signData = new HashMap<String, String>();
            signData.put("username", principal.getUsername());
            signData.put("nickName", principal.getNickName());
            signData.put("depId", principal.getDepId());
            signData.put("depName", principal.getDepName());
            signData.put("orgId", principal.getOrgId());
            String token = jwtTokenProvider.generateToken(signData, principal.getAuthorities());
            ResponseResult r = ResponseResult.ok("登录成功！");
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("username", principal.getUsername());
            data.put("nickName", principal.getNickName());
            data.put("accessToken", token);
            r.setData(data);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(JacksonUtils.toJson(r).getBytes(CharsetUtil.UTF_8))));
        }
    }

    public class DefaultAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

        /**
         * onAuthenticationFailure
         *
         * @param webFilterExchange
         * @param exception
         * @return
         */
        public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
            ServerWebExchange exchange = webFilterExchange.getExchange();
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            ResponseResult r = ResponseResult.error(exception.getMessage());
            if (exception.getMessage().indexOf("Bad captcha") != -1) {
                r = ResponseResult.error("验证码错误！");
            } else if (exception.getMessage().indexOf("Bad credentials") != -1) {
                r = ResponseResult.error("用户名或密码错误！");
            } else if (exception.getMessage().indexOf("Bad grant") != -1) {
                r = ResponseResult.error(" 用户没有操作权限，请联系管理员！");
            }
            return response.writeWith(Mono.just(response.bufferFactory().wrap(JacksonUtils.toJson(r).getBytes(CharsetUtil.UTF_8))));
        }
    }

    public class DefaultServerLogoutHandler implements ServerLogoutHandler {

        /**
         * logout
         *
         * @param exchange
         * @param authentication
         * @return
         */
        public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
            if (authentication != null) {
                JwtUser user = (JwtUser) authentication.getPrincipal();
                String username = user.getUsername();
                log.info("username: {}  is offline now", username);
            }
            return null;
        }
    }

    public class DefaultServerLogoutSuccessHandler implements ServerLogoutSuccessHandler {

        /**
         * onLogoutSuccess
         *
         * @param exchange
         * @param authentication
         * @return
         */
        public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
            ServerHttpResponse response = exchange.getExchange().getResponse();
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            response.setStatusCode(HttpStatus.OK);
            ResponseResult r = ResponseResult.ok("退出成功！");
            return response.writeWith(Mono.just(response.bufferFactory().wrap(JacksonUtils.toJson(r).getBytes(CharsetUtil.UTF_8))));
        }
    }
}
