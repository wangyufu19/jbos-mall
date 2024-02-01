package com.mall.gateway.common.websecurity.user;

import com.mall.gateway.application.service.auth.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * USER_ADMIN
     */
    private static final String USER_ADMIN = "admin";
    /**
     * ROLE_ADMIN
     */
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    @Autowired
    private UserAuthService userAuthService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        //用户认证
        Map<String, Object> userMap = userAuthService.login(username);
        if (userMap == null) {
            throw new BadCredentialsException("Bad credentials");
        } else {
            //查询用户权限
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            List<HashMap<String, String>> userPermissionList = userAuthService.getAuthUserPermission(username);
            if (USER_ADMIN.equals(username)) {
                grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_ADMIN));
            }
            for (HashMap role : userPermissionList) {
                grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(role.get("FUNCCODE"))));
            }
            if (ObjectUtils.isEmpty(grantedAuthorities)) {
                throw new AccountGrantException("Bad grant");
            }
            String nickName = String.valueOf(userMap.get("NICKNAME"));
            String password = String.valueOf(userMap.get("PASSWORD"));
            String depId = String.valueOf(userMap.get("DEPID"));
            String depName = String.valueOf(userMap.get("DEPNAME"));
            String orgId = String.valueOf(userMap.get("ORGID"));
            JwtUser jwtUser = new JwtUser();
            jwtUser.setUsername(username);
            jwtUser.setNickName(nickName);
            jwtUser.setPassword(password);
            jwtUser.setDepId(depId);
            jwtUser.setDepName(depName);
            jwtUser.setOrgId(orgId);
            jwtUser.setAuthorities(grantedAuthorities);
            return jwtUser;
        }
    }

    public class AccountGrantException extends AuthenticationException {
        /**
         * AccountGrantException
         *
         * @param msg
         */
        public AccountGrantException(String msg) {
            super(msg);
        }

        /**
         * AccountGrantException
         *
         * @param msg
         * @param t
         */
        public AccountGrantException(String msg, Throwable t) {
            super(msg, t);
        }
    }
}
