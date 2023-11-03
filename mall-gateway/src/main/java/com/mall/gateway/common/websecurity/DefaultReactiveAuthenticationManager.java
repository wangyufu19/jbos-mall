package com.mall.gateway.common.websecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractUserDetailsReactiveAuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

/**
 * DefaultReactiveAuthenticationManager
 *
 * @author youfu.wang
 * @date 2023/10/26 13:37
 */
@Configuration
public class DefaultReactiveAuthenticationManager extends AbstractUserDetailsReactiveAuthenticationManager {
    /**
     * userDetailsService
     */
    @Autowired
    private ReactiveUserDetailsService userDetailsService;
    /**
     * passwordEncoder
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * authenticate
     * @param authentication
     * @return Authentication
     */
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        return retrieveUser(username)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BadCredentialsException("账号或密码错误！"))))
                .map(u -> new UsernamePasswordAuthenticationToken(u, u.getPassword()));

    }

    protected Mono<UserDetails> retrieveUser(String username) {
        return userDetailsService.findByUsername(username);
    }
}
