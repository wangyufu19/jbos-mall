//package com.mall.admin.common.user;
//
//import com.mall.admin.application.service.auth.UserAuthService;
//import com.mall.common.utils.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * UserDetailsServiceImpl
// * @author youfu.wang
// * @date 2021-05-21
// */
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    private UserAuthService userAuthService;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //用户认证
//        Map<String,Object> userMap=userAuthService.login(username);
//        if(userMap==null){
//            throw new InternalAuthenticationServiceException("用户认证失败");
//        }else{
//            //判断用户权限
//            List<HashMap> userRoles=userAuthService.getAuthUserRole(username);
//            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//            for (HashMap role : userRoles) {
//                grantedAuthorities.add(new SimpleGrantedAuthority(StringUtils.replaceNull(role.get("ROLECODE"))));
//            }
//            String userInfo=StringUtils.replaceNull(userMap.get("USERNAME"));
//            String password=StringUtils.replaceNull(userMap.get("PASSWORD"));
//            return new JwtUser(username,userInfo,password,grantedAuthorities);
//        }
//    }
//}
