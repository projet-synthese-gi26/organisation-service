package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProposedActivityDTO {

    @Schema(description = "Requête de création d'activité/service")
    public record Request(
            @NotNull UUID organizationId,
            @NotBlank String name,
            @Schema(description = "Type de service", example = "CONSULTING")
            String type,
            @Schema(description = "Prix ou Taux horaire")
            BigDecimal rate,
            String description
    ) {}

    public record Response(
            UUID id,
            UUID organizationId,
            String name,
            String type,
            BigDecimal rate,
            String description,
            LocalDateTime createdAt
    ) {}
}