package com.apigestionespacios.apigestionespacios.JWT.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

public class usuarioJwt implements org.springframework.security.core.userdetails.UserDetails {

    private String username;
    private String password;
    private Set<? extends GrantedAuthority> roles;

    public usuarioJwt(String username, String password, Set<? extends GrantedAuthority> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
