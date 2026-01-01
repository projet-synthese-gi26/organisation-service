package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class CertificationDTO {

    @Schema(name = "CertificationRequest", description = "Requête d'ajout de certification")
    public record Request(
            @NotNull UUID organizationId,
            
            @NotBlank 
            @Schema(example = "ISO 9001")
            String name,
            
            @NotBlank 
            @Schema(example = "QUALITY_LABEL")
            String type,
            
            String description,
            
            @Schema(description = "Date d'obtention")
            LocalDateTime obtainementDate
    ) {}

    @Schema(name = "CertificationResponse", description = "Réponse Certification")
    public record Response(
            UUID id,
            UUID organizationId,
            String name,
            String type,
            String description,
            LocalDateTime obtainementDate,
            LocalDateTime createdAt
    ) {}
}