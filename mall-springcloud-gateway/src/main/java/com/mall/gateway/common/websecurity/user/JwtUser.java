package com.mall.gateway.common.websecurity.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * JwtUser
 * @author youfu.wang
 * @date 2021-05-21
 */
public class JwtUser implements UserDetails {
    public static final String AUTHORITIES = "authorities";
    private String username;
    private String nickName;
    private String password;
    private String depId;
    private String depName;
    private String orgId;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired=true;
    private boolean accountNonLocked=true;
    private boolean credentialsNonExpired=true;
    private boolean enabled=true;

    public JwtUser(){

    }
    public JwtUser(
            String username,
            String nickName,
            Collection<? extends GrantedAuthority> authorities){
        this.username=username;
        this.nickName=nickName;
        this.authorities=authorities;
    }
    public JwtUser(
            String username,
            String nickName,
            String password,
            String depId,
            String depName,
            String orgId,
            Collection<? extends GrantedAuthority> authorities){
        this.username=username;
        this.nickName=nickName;
        this.password=password;
        this.depId=depId;
        this.orgId=orgId;
        this.authorities=authorities;
    }
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities){
        this.authorities=authorities;
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword() {
        return this.password;
    }

    public void setUsername(String username){
        this.username=username;
    }
    public String getUsername() {
        return this.username;
    }
    public void setNickName(String nickName){
        this.nickName=nickName;
    }
    public String getNickName(){
            return this.nickName;
    }
    public void setDepId(String depId){
        this.depId=depId;
    }
    public String getDepId(){
        return this.depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }
    public void setOrgId(String orgId){
        this.orgId=orgId;
    }
    public String getOrgId(){
        return this.orgId;
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
