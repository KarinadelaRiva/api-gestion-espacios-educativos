package com.apigestionespacios.apigestionespacios.entities.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Rol implements GrantedAuthority {
    ADMIN,
    PROFESOR;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }

    public static boolean isValid(String rol) {
        for (Rol r : Rol.values()) {
            if (r.name().equalsIgnoreCase(rol)) {
                return true;
            }
        }
        return false;
    }
}

