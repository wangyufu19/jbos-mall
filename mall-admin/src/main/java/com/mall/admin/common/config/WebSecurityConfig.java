package com.mall.admin.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.admin.application.service.auth.CaptchaService;
import com.mall.admin.common.user.JwtUser;
import com.mall.common.jwt.JwtTokenProvider;
import com.mall.common.response.ResponseData;
import com.mall.common.utils.JacksonUtils;
import com.mall.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * WebSecurityConfig
 * @author youfu.wang
 * @date 2021-05-21
 */
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true ,jsr250Enabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Value("${spring.security.filter.loginUri}")
    private String loginUri;
    @Value("${spring.security.filter.excludeUri}")
    private String excludeUri;
    /**
     * ??????????????????
     * @param auth
     * @throws Exception
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * ????????????????????????
         */
        auth.userDetailsService(userDetailsService);
    }
    public void configure(WebSecurity web) throws Exception {
        String[] excludeUris=excludeUri.split(",");
        web.ignoring().antMatchers(excludeUris);
    }

    /**
     * HTTP????????????
     * @param http
     * @throws Exception
     */
    protected void configure(HttpSecurity http) throws Exception {
        String[] excludeUris=excludeUri.split(",");
        http
                .csrf().disable()  //??????csrf
                .formLogin().disable() //??????form??????
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()  //??????session
                .authorizeRequests()
                .antMatchers(excludeUris).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAt(loginFilter(),UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtAuthenticationFilter(),LoginFilter.class)
                .logout()
                .invalidateHttpSession(true)
                .addLogoutHandler(new WebLogoutHandler()).logoutSuccessHandler(new WebLogoutSuccessHandler())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint(){
                    //??????????????????????????????????????????
                    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException {
                        ResponseData r= ResponseData.error("403","????????????????????????");
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        out.write(JacksonUtils.toJson(r));
                        out.flush();
                        out.close();
                    }
                })
                .and().cors(); //??????????????????
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        // ??????????????????
        loginFilter.setFilterProcessesUrl(this.loginUri);
        // ???????????????????????????
        loginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
                JwtUser principal = (JwtUser) authentication.getPrincipal(); // ??????????????????
                Map<String,String> signData=new HashMap<String,String>();
                signData.put("username",principal.getUsername());
                signData.put("userInfo",principal.getUserInfo());
                String token = jwtTokenProvider.generateToken(signData);
                ResponseData r= ResponseData.ok("???????????????");
                Map<String,Object> data=new HashMap<String,Object>();
                data.put("username", principal.getUsername());
                data.put("userInfo",principal.getUserInfo());
                data.put("accessToken", token);
                r.setData(data);
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write(JacksonUtils.toJson(r));
                out.flush();
                out.close();
            }
        });
        // ???????????????????????????
        loginFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
                ResponseData r= ResponseData.error(e.getMessage());
                if(e.getMessage().indexOf("Bad captcha")!=-1){
                    r= ResponseData.error("??????????????????");
                }else if(e.getMessage().indexOf("Bad credentials")!=-1){
                    r= ResponseData.error("???????????????????????????");
                }
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write(JacksonUtils.toJson(r));
                out.flush();
                out.close();
            }
        });
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        return loginFilter;
    }
    /**
     * ????????? UsernamePasswordAuthenticationFilter ?????????
     */
    public class LoginFilter extends UsernamePasswordAuthenticationFilter {
        @Autowired
        private CaptchaService captchaService;

        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            if (!request.getMethod().equals("POST")) {
                throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
            }
            // ?????????????????????json?????????????????????????????????????????????????????????
            if (request.getContentType().indexOf(MediaType.APPLICATION_JSON_VALUE)!=-1) {
                // ???request???json???????????????Map
                Map<String, String> loginData = new HashMap<>();
                try {
                    loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // ???????????????
                String captchaToken=StringUtils.replaceNull(loginData.get("captchaToken"));
                String captchaText=StringUtils.replaceNull(loginData.get("captchaText"));
                if(!captchaService.validate(captchaToken,captchaText)){
                   throw new CaptchaAuthenticationException("Bad captcha");
                }
                // ???????????????getParameter() ????????????key???
                String username = StringUtils.replaceNull(loginData.get(this.getUsernameParameter()));
                String password = StringUtils.replaceNull(loginData.get(this.getPasswordParameter()));
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username.trim(), password);
                this.setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } else {
                return super.attemptAuthentication(request, response);
            }
        }
    }
    public class CaptchaAuthenticationException extends AuthenticationException{

        public CaptchaAuthenticationException(String msg, Throwable t) {
            super(msg, t);
        }
        public CaptchaAuthenticationException(String msg) {
            super(msg);
        }
    }
    public class WebLogoutHandler implements LogoutHandler {

        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            if(authentication!=null){
                JwtUser user = (JwtUser) authentication.getPrincipal();
                String username = user.getUsername();
                log.info("username: {}  is offline now", username);
            }
        }
    }
    public class WebLogoutSuccessHandler implements LogoutSuccessHandler{

        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            authentication=SecurityContextHolder.getContext().getAuthentication();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("utf-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter out = response.getWriter();
            ResponseData r=ResponseData.ok("???????????????");
            out.write(JacksonUtils.toJson(r));
            out.flush();
            out.close();
        }
    }
    public class JwtAuthenticationFilter extends OncePerRequestFilter {
        private AntPathMatcher antPathMatcher=new AntPathMatcher(File.separator);
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            try {
                String requestURI=request.getRequestURI();
                String[] excludeUris=excludeUri.split(",");
                boolean matches=false;
                for(String uri:excludeUris){
                    matches=antPathMatcher.match(uri,requestURI);
                    if(matches){
                        break;
                    }
                }
                if(matches){
                    filterChain.doFilter(request, response);
                    return;
                }

                String token = getRequestToken(request);
                if (token == null || !jwtTokenProvider.verifyToken(token)) {
                    ResponseData r = ResponseData.error("token????????????????????????");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(JacksonUtils.toJson(r));
                    out.flush();
                    out.close();
                    return;
                }
                UsernamePasswordAuthenticationToken authentication;
                String username = jwtTokenProvider.getSignDataFromJWT(token, "username");
                String userInfo = jwtTokenProvider.getSignDataFromJWT(token, "userInfo");
                UserDetails userDetails = new JwtUser(username, userInfo,null, null);
                // ??????????????????token
                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                if (authentication != null) {
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                }
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                logger.error("????????? Security ?????????????????????????????????", e);
            }
            filterChain.doFilter(request, response);
        }

        private String getRequestToken(HttpServletRequest request) {
            String accessToken = request.getHeader("accessToken");
            if (accessToken == null) {
                return request.getParameter("accessToken");
            } else {
                return accessToken;
            }
        }
    }


}
