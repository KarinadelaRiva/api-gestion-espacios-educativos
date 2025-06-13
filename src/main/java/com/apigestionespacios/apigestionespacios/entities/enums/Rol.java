package com.apigestionespacios.apigestionespacios.entities.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Rol implements GrantedAuthority {
    ADMIN,
    PROFESOR;

    @Override
    public String getAuthority() {
        return "";
    }
}

