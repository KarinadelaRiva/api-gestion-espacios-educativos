package com.apigestionespacios.apigestionespacios.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Espacios")
                        .version("1.0")
                        .description("Documentación de la API REST para el sistema de gestión de espacios.")
                        .contact(new Contact()
                                .name("Gastón Buesas, Nicolas Ambrosini, Karina de la Riva y Camila Setacovsky")
                        )
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/**")
                .build();
    }
}

