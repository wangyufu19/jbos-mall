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
     * userAuthService
     */
    @Autowired
    private UserAuthService userAuthService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        //用户认证
        Map<String, Object> userMap = userAuthService.login(username);
        if (userMap == null) {
            throw new BadCredentialsException("Bad credentials");
        } else {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            //查询用户角色
            List<String> userRoleList = userAuthService.getAuthUserRole(username);
            if (!ObjectUtils.isEmpty(userRoleList)) {
                for (String role : userRoleList) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(role));
                }
            }
            //查询用户权限
            List<String> userPermissionList = userAuthService.getAuthUserPermission(username);
            if (!ObjectUtils.isEmpty(userPermissionList)) {
                for (String permission : userPermissionList) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(permission));
                }
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
