package com.mall.gateway.common.websecurity.user;

import com.mall.common.utils.StringUtils;
import com.mall.gateway.application.auth.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserDetailsServiceImpl
 *
 * @author youfu.wang
 * @date 2021-05-21
 */
@Service
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {
    private final String USER_ADMIN = "admin";
    private final String ROLE_ADMIN = "ROLE_ADMIN";
    @Autowired
    private UserAuthService userAuthService;
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        //用户认证
        Map<String, Object> userMap = userAuthService.login(username);
        if (userMap == null) {
            throw new BadCredentialsException("Bad credentials");
        } else {
            //判断用户权限
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            List<HashMap> userRoles = userAuthService.getAuthUserRole(username);
            if (USER_ADMIN.equals(username)) {
                grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_ADMIN));
            } else {
                if (userRoles == null || userRoles.size() == 0) {
                    throw new AccountGrantException("Bad grant");
                }
                for (HashMap role : userRoles) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(StringUtils.replaceNull(role.get("ROLECODE"))));
                }
            }
            String nickName = StringUtils.replaceNull(userMap.get("NICKNAME"));
            String password = StringUtils.replaceNull(userMap.get("PASSWORD"));
            String depId = StringUtils.replaceNull(userMap.get("DEPID"));
            String orgId = StringUtils.replaceNull(userMap.get("ORGID"));
            UserDetailsExt userDetails = new UserDetailsExt();
            userDetails.setUsername(username);
            userDetails.setNickName(nickName);
            userDetails.setPassword(password);
            userDetails.setDepId(depId);
            userDetails.setOrgId(orgId);
            userDetails.setAuthorities(grantedAuthorities);
            return Mono.just(userDetails);
        }
    }


    public class AccountGrantException extends AuthenticationException {
        public AccountGrantException(String msg) {
            super(msg);
        }

        public AccountGrantException(String msg, Throwable t) {
            super(msg, t);
        }
    }
}
