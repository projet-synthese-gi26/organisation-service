package com.yowyob.organisation_service.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder; // <--- Import pour le filtre
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import jakarta.annotation.PostConstruct; // <--- CORRECTION ICI (jakarta au lieu de javax)
import javax.crypto.spec.SecretKeySpec; // javax.crypto fait partie du JDK, donc ça ne change pas
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${application.security.jwt.secret}")
    private String jwtSecret;

    @Value("${application.cors.allowed-origins}")
    private String allowedOrigins;

    @PostConstruct
    public void logSecretStatus() {
        if (jwtSecret == null || jwtSecret.isEmpty()) {
            log.error("❌ LA CLÉ JWT EST VIDE OU NULLE ! Vérifiez application.yml");
        } else {
            // Affiche les 5 premiers caractères pour vérifier si c'est la bonne clé
            log.info("✅ Clé JWT chargée. Longueur: {} chars. Début: '{}...'",
                    jwtSecret.length(),
                    jwtSecret.length() > 5 ? jwtSecret.substring(0, 5) : jwtSecret);
        }
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // --- FILTRE DE DEBUG ---
                .addFilterBefore((exchange, chain) -> {
                    var request = exchange.getRequest();
                    var authHeader = request.getHeaders().getFirst("Authorization");

                    log.info("📥 REQUÊTE : {} {}", request.getMethod(), request.getPath());

                    if (authHeader != null) {
                        String masked = authHeader.length() > 20 ? authHeader.substring(0, 20) + "..." : authHeader;
                        log.info("🔑 Header Authorization : '{}'", masked);
                    } else {
                        log.warn("⚠️ AUCUN Header Authorization trouvé !");
                    }
                    return chain.filter(exchange);
                }, SecurityWebFiltersOrder.HTTP_BASIC) // <--- CORRECTION ICI
                // -----------------------

                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**")
                        .permitAll()
                        .pathMatchers("/actuator/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtDecoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint((exchange, ex) -> {
                            log.error("⛔ ERREUR 401 : {}", ex.getMessage());
                            if (ex instanceof OAuth2AuthenticationException oauthEx) {
                                // Affiche l'erreur détaillée (signature invalide, expiré, malformé...)
                                log.error("Détails OAuth2 : {}", oauthEx.getError());
                            }
                            return Mono.fromRunnable(() -> exchange.getResponse()
                                    .setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED));
                        }))
                .build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        // Lecture de la clé en UTF-8 (Raw String) comme dans Auth Service
        byte[] secretKeyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKey = new SecretKeySpec(secretKeyBytes, "HmacSHA256");

        return NimbusReactiveJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        ReactiveJwtAuthenticationConverter jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                new ReactiveJwtGrantedAuthoritiesConverterAdapter(grantedAuthoritiesConverter));
        return jwtAuthenticationConverter;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        if ("*".equals(allowedOrigins)) {
            config.setAllowedOriginPatterns(List.of("*"));
        } else {
            config.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        }
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}