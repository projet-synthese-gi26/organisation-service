package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class AffiliationDTO {

    @Schema(description = "Requête pour affilier un acteur à une structure")
    public record Request(
            @NotNull UUID actorId,
            @NotNull UUID targetId, // ID de l'agence ou de l'organisation
            @Schema(description = "Type de relation", example = "MEMBER", allowableValues = {"MEMBER", "MANAGER", "PARTNER"})
            String type
    ) {}

    @Schema(description = "Réponse d'affiliation")
    public record Response(
            UUID id,
            UUID actorId,
            UUID targetId, // organizationId ou agencyId selon le cas
            String type,
            Boolean isActive,
            LocalDateTime createdAt
    ) {}
}