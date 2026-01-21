package com.yowyob.organisation_service.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Organisation API")
                        .version("1.0.0")
                        .description("Documentation de l'API Organisation (Reactive)"))
                
                // 1. Active la sécurité sur toutes les routes
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                
                // 2. Configure le schéma pour dire à Swagger : "C'est du HTTP Bearer"
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP) 
                                        .scheme("bearer")              
                                        .bearerFormat("JWT")
                        ));
    }
}