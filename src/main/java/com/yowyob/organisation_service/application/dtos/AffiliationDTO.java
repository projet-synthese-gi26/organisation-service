package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class AffiliationDTO {

    @Schema(name = "AffiliationRequest", description = "Requête pour affilier un acteur à une structure")
    public record Request(
            @NotNull 
            @Schema(description = "ID de l'acteur (Employé, Business Actor...)", example = "123e4567-e89b-12d3-a456-426614174000")
            UUID actorId,

            @NotNull 
            @Schema(description = "ID de la cible (Organisation ou Agence)", example = "987e6543-e21b-12d3-a456-426614174000")
            UUID targetId, 

            @Schema(description = "Type de relation", example = "MEMBER", allowableValues = {"MEMBER", "MANAGER", "PARTNER", "CONSULTANT"})
            String type
    ) {}

    @Schema(name = "AffiliationResponse", description = "Réponse d'affiliation")
    public record Response(
            UUID id,
            UUID actorId,
            UUID targetId, 
            String type,
            Boolean isActive,
            LocalDateTime createdAt
    ) {}
}