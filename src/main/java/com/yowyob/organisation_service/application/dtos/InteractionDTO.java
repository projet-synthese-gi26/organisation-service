package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class InteractionDTO {

    public record Request(
            @NotNull UUID prospectId,
            @Schema(description = "Notes sur l'échange")
            String notes,
            LocalDateTime interactionDate
    ) {}

    public record Response(
            UUID id,
            UUID prospectId,
            String notes,
            LocalDateTime interactionDate,
            LocalDateTime createdAt
    ) {}
}