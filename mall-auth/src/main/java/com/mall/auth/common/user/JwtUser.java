package com.mall.auth.common.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * JwtUser
 * @author youfu.wang
 * @date 2021-05-21
 */
public class JwtUser implements UserDetails {
    private String username;
    private String userInfo;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired=true;
    private boolean accountNonLocked=true;
    private boolean credentialsNonExpired=true;
    private boolean enabled=true;

    public JwtUser(String username, String password,Collection<? extends GrantedAuthority> authorities){
        this.username=username;
        this.password=password;
        this.authorities=authorities;
    }
    public JwtUser(String username, String userInfo,String password,Collection<? extends GrantedAuthority> authorities){
        this.username=username;
        this.userInfo=userInfo;
        this.password=password;
        this.authorities=authorities;
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return this.password;
    }


    public String getUsername() {
        return this.username;
    }
    public String getUserInfo(){
            return this.userInfo;
    }
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
