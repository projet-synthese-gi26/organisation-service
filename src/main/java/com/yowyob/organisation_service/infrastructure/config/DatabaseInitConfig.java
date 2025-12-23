package com.yowyob.organisation_service.infrastructure.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Configuration
public class DatabaseInitConfig {

    private final DatabaseClient databaseClient;

    public DatabaseInitConfig(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @PostConstruct
    public void init() {
        try {
            ClassPathResource resource = new ClassPathResource("schema.sql");
            // Lecture compatible Linux/Windows
            String sql = new String(resource.getInputStream().readAllBytes());

            // Découpage basique sur le point-virgule (Attention aux triggers/fonctions complexes)
            String[] statements = sql.split(";");

            Flux.fromArray(statements)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .flatMap(s -> databaseClient.sql(s).then())
                    .subscribe(
                            null,
                            err -> System.err.println("❌ Erreur init DB : " + err.getMessage()),
                            () -> System.out.println("✅ Base de données initialisée avec succès (Tables créées)")
                    );
        } catch (Exception e) {
            System.err.println("⚠️ Impossible de lire schema.sql : " + e.getMessage());
        }
    }
}