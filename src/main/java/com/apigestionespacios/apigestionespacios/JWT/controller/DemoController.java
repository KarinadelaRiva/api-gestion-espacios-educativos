package com.apigestionespacios.apigestionespacios.JWT.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    // Este endpoint solo se puede acceder con un token válido
    @GetMapping("/protegido")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String rutaProtegida() {
        return "Acceso autorizado. Estás autenticado correctamente.";
    }
}
