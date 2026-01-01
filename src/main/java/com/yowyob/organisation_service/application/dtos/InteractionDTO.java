package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class InteractionDTO {

    @Schema(name = "InteractionRequest", description = "Requête d'ajout d'interaction")
    public record Request(
            @NotNull 
            @Schema(description = "ID du prospect concerné")
            UUID prospectId,
            
            @Schema(description = "Notes sur l'échange", example = "Intéressé par l'offre premium, à relancer mardi.")
            String notes,
            
            @Schema(description = "Date de l'interaction (défaut: maintenant)")
            LocalDateTime interactionDate
    ) {}

    @Schema(name = "InteractionResponse", description = "Réponse Interaction")
    public record Response(
            UUID id,
            UUID prospectId,
            String notes,
            LocalDateTime interactionDate,
            LocalDateTime createdAt
    ) {}
}