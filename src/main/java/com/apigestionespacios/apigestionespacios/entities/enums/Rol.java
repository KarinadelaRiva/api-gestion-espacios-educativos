package com.apigestionespacios.apigestionespacios.entities.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Rol implements GrantedAuthority {
    ADMINISTRADOR,
    PROFESOR;

    @Override
    public String getAuthority() {
        return "";
    }
}

