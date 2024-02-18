package com.mall.admin.common.shiro;

import com.mall.admin.common.jwt.JwtTokenProvider;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * AuthRealm
 *
 * @author youfu.wang
 * @date 2024/2/6 11:15
 */

public class AuthRealm extends AuthorizingRealm {
    /**
     * jwtTokenProvider
     */
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AuthToken;
    }

    /**
     * 访问授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = String.valueOf(SecurityUtils.getSubject().getPrincipal());
        List<String> authorities = jwtTokenProvider.getGrantedAuthorityFromJWT(principal, "authorities");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (!ObjectUtils.isEmpty(authorities)) {
            for (String authority : authorities) {
                info.addStringPermission(authority);
            }
        }
        return info;
    }

    /**
     * 用户授权信息
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String accessToken = (String) authenticationToken.getPrincipal();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(accessToken, accessToken, getName());
        return info;
    }
}
