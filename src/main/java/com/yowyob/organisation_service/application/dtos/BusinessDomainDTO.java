package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

public class BusinessDomainDTO {

    @Schema(description = "Requête de création d'un domaine métier")
    public record Request(
            @NotBlank(message = "Le code est obligatoire")
            String code,

            @Schema(description = "ID du domaine parent (pour sous-catégories)", nullable = true)
            UUID parentId,

            @NotBlank(message = "Le nom est obligatoire")
            String name,

            String service, // Ex: "COMMERCE", "SANTE"
            String type,
            String typeLabel,
            String description,
            String imageUri
    ) {}

    @Schema(description = "Réponse Domaine Métier")
    public record Response(
            UUID id,
            String code,
            UUID parentId,
            String name,
            String service,
            String type,
            String typeLabel,
            String description,
            String imageUri,
            LocalDateTime createdAt
    ) {}
}