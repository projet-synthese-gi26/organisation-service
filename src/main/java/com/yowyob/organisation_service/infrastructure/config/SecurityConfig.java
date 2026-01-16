package com.yowyob.organisation_service.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${application.security.jwt.secret}")
    private String jwtSecret;

    @Value("${application.cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                // 1. Désactiver CSRF (inutile pour API REST stateless)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                
                // 2. Activer et configurer CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                
                // 3. Règles d'autorisation des routes
                .authorizeExchange(exchanges -> exchanges
                        // Swagger UI / OpenAPI (Public)
                        .pathMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // Actuator (Monitoring) - À sécuriser en PROD idéalement, mais public pour l'instant
                        .pathMatchers("/actuator/**").permitAll()
                        // Tout le reste nécessite d'être authentifié
                        .anyExchange().authenticated()
                )
                
                // 4. Configurer le Resource Server (Validation JWT)
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtDecoder(jwtDecoder()))
                )
                .build();
    }

    /**
     * Configuration CORS stricte pour la Production
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // MODIFICATION ICI : Gestion du cas "*" (Tout le monde)
        if ("*".equals(allowedOrigins)) {
            // Cette méthode permet "toutes les origines" MÊME avec allowCredentials = true
            configuration.setAllowedOriginPatterns(List.of("*"));
        } else {
            // Comportement standard pour une liste explicite
            configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        }
        
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true); 
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Décodeur JWT configuré pour HS256 (Symétrique)
     */
    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        // Conversion de la clé hexadécimale ou textuelle en SecretKeySpec
        // ATTENTION : Si votre clé dans le properties est du texte brut, utilisez .getBytes()
        // Si c'est du Hexa, il faudrait un convertisseur Hex -> Bytes.
        // D'après votre exemple ("404E..."), cela ressemble à de l'Hexadécimal.
        // Cependant, souvent les gens mettent la clé en texte brut.
        
        // HYPOTHÈSE : La clé fournie est une chaîne HEXADÉCIMALE représentant des octets.
        // Si c'est juste une "passphrase" texte, utilisez jwtSecret.getBytes(StandardCharsets.UTF_8).
        // Vu la longueur et les caractères (0-9, A-F), je parie sur du HEX.
        
        byte[] secretKeyBytes = hexStringToByteArray(jwtSecret);
        
        SecretKeySpec secretKey = new SecretKeySpec(secretKeyBytes, "HmacSHA256");

        return NimbusReactiveJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    // Helper pour convertir le HEX string en bytes
    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}