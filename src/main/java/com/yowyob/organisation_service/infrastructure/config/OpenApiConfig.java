package com.yowyob.organisation_service.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server; // <--- Import
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Organisation API", version = "1.0.0", description = "Documentation de l'API Organisation"), servers = {
                @Server(url = "http://localhost:8081", description = "Serveur Local") // <--- AJOUT IMPORTANT
})

public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                final String securitySchemeName = "bearerAuth";

                return new OpenAPI()
                        .info(new io.swagger.v3.oas.models.info.Info()
                                .title("Organisation API")
                                .version("1.0.0")
                                .description("Documentation de l'API Organisation (Reactive)"))
                                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                                .components(new Components()
                                                .addSecuritySchemes(securitySchemeName,
                                                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                                                                .name(securitySchemeName)
                                                                                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                                                                .scheme("bearer")
                                                                                .bearerFormat("JWT")));
        }
}