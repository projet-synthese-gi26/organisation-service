package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

public class BusinessDomainDTO {

    @Schema(name = "BusinessDomainRequest", description = "Requête de création d'un domaine métier")
    public record Request(
            @NotBlank(message = "Le code est obligatoire")
            @Schema(example = "IT_DEV")
            String code,

            @Schema(description = "ID du domaine parent (pour sous-catégories)", nullable = true)
            UUID parentId,

            @NotBlank(message = "Le nom est obligatoire")
            @Schema(example = "Développement Logiciel")
            String name,

            @Schema(description = "Macro-service associé", example = "IT_SERVICES")
            String service, 
            
            @Schema(example = "SECTOR")
            String type,
            
            @Schema(example = "Secteur Numérique")
            String typeLabel,
            
            String description,
            String imageUri
    ) {}

    @Schema(name = "BusinessDomainResponse", description = "Réponse Domaine Métier")
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