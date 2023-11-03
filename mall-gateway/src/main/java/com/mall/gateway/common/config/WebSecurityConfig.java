package com.mall.gateway.common.config;

import com.alibaba.nacos.common.utils.JacksonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.response.ResponseResult;
import com.mall.gateway.common.config.properties.WebSecurityProperties;
import com.mall.gateway.common.util.WebRequestUtil;
import com.mall.gateway.common.websecurity.DefaultReactiveAuthenticationManager;
import com.mall.gateway.common.websecurity.jwt.JwtTokenProvider;
import com.mall.gateway.common.websecurity.user.UserDetailsExt;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
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
public class WebSecurityConfig {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private WebSecurityProperties webSecurityProperties;
    @Autowired
    private DefaultReactiveAuthenticationManager defaultReactiveAuthenticationManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        String[] excludeUris = WebRequestUtil.splitExcludeUri(webSecurityProperties.getExcludeUri());
        httpSecurity
                .csrf().disable()  //禁用csrf
                .httpBasic().disable() //httpBasic
                .formLogin().disable() //formLogin
                .securityContextRepository(new DefaultSecurityContextRepository(webSecurityProperties.getLoginUri()))
                // 请求拦截处理
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(excludeUris).permitAll()
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(this.authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
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
                .cors().configurationSource(new DefaultCorsConfigurationSource()); //启用跨域请求
        return httpSecurity.build();
    }


    /**
     * BCrypt密码编码
     */
    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    public class DefaultCorsConfigurationSource implements CorsConfigurationSource {

        public CorsConfiguration getCorsConfiguration(ServerWebExchange exchange) {
            CorsConfiguration config = new CorsConfiguration();
            config.addAllowedOriginPattern("*");
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            config.setAllowCredentials(true);
            config.setMaxAge(3600L);
            return config;
        }
    }

    public class DefaultSecurityContextRepository implements ServerSecurityContextRepository {
        private String loginUrl = "/login";

        public DefaultSecurityContextRepository(String loginUrl) {
            this.loginUrl = loginUrl;
        }

        @Override
        public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
            return Mono.empty();
        }

        @Override
        public Mono<SecurityContext> load(ServerWebExchange exchange) {
            ServerHttpRequest request = exchange.getRequest();
            String requestURI = request.getURI().getPath();
            // 登录URI无需存储用户安全认证
            if (PathPatternParser.defaultInstance.parse(this.loginUrl).matches(PathContainer.parsePath(requestURI))) {
                return Mono.empty();
            }
            String accessToken = WebRequestUtil.getRequestToken(request);
            if (StringUtils.hasLength(accessToken)) {
                String username = jwtTokenProvider.getSignDataFromJWT(accessToken, UserDetailsExt.USERNAME_PARAMETER);
                String nickName = jwtTokenProvider.getSignDataFromJWT(accessToken, UserDetailsExt.NICKNAME_PARAMETER);
                UserDetails userDetails = new UserDetailsExt(username, nickName, null);
                // 构建认证过的token
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                return Mono.just(authentication).map(SecurityContextImpl::new);
            }
            return Mono.empty();
        }
    }


    private AuthenticationWebFilter authenticationWebFilter() {

        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(this.defaultReactiveAuthenticationManager);
        authenticationWebFilter.setSecurityContextRepository(new DefaultSecurityContextRepository(webSecurityProperties.getLoginUri()));
        authenticationWebFilter.setServerAuthenticationConverter(new ServerAuthenticationConverter() {
            private String usernameParameter = "username";
            private String passwordParameter = "password";

            @Override
            public Mono<Authentication> convert(ServerWebExchange exchange) {
                MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
                if (mediaType != null && mediaType.toString().startsWith(MediaType.APPLICATION_JSON_VALUE)) {
                    return DataBufferUtils.join(exchange.getRequest().getBody())
                            .flatMap(dataBuffer -> {
                                byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(bytes);
                                // 把request的json数据转换为Map
                                Map<String, String> loginData = new HashMap<>();
                                try {
                                    loginData = new ObjectMapper().readValue(bytes, Map.class);
                                } catch (IOException e) {
                                    log.error(e.getMessage());
                                }
                                DataBufferUtils.release(dataBuffer);
                                String username = loginData.get(usernameParameter);
                                String password = loginData.get(passwordParameter);
                                return Mono.just(new UsernamePasswordAuthenticationToken(username, password));
                            });
                }
                return Mono.empty();
            }
        });
        authenticationWebFilter.setAuthenticationSuccessHandler(new DefaultAuthenticationSuccessHandler());
        authenticationWebFilter.setAuthenticationFailureHandler(new DefaultAuthenticationFailureHandler());
        authenticationWebFilter.setRequiresAuthenticationMatcher(
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, this.webSecurityProperties.getLoginUri())
        );
        return authenticationWebFilter;
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
            response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
            //获取用户对象
            UserDetailsExt principal = (UserDetailsExt) authentication.getPrincipal(); // 获取用户对象
            Map<String, String> signData = new HashMap<String, String>();
            signData.put(UserDetailsExt.USERNAME_PARAMETER, principal.getUsername());
            signData.put(UserDetailsExt.NICKNAME_PARAMETER, principal.getNickName());
            signData.put(UserDetailsExt.DEPID_PARAMETER, principal.getDepId());
            signData.put(UserDetailsExt.ORGID_PARAMETER, principal.getOrgId());
            String token = jwtTokenProvider.generateToken(signData);
            ResponseResult r = ResponseResult.ok("登录成功！");
            Map<String, Object> data = new HashMap<String, Object>();
            data.put(UserDetailsExt.USERNAME_PARAMETER, principal.getUsername());
            data.put(UserDetailsExt.NICKNAME_PARAMETER, principal.getNickName());
            data.put(JwtTokenProvider.TOKEN, token);
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
            response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
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
                ReactiveSecurityContextHolder.clearContext();
                UserDetailsExt user = (UserDetailsExt) authentication.getPrincipal();
                String username = user.getUsername();
                log.info("username: {}  is offline now", username);
            }
            return Mono.empty();
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
            response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatusCode(HttpStatus.OK);
            ResponseResult r = ResponseResult.ok("退出成功！");
            return response.writeWith(Mono.just(response.bufferFactory().wrap(JacksonUtils.toJson(r).getBytes(CharsetUtil.UTF_8))));
        }
    }
}
