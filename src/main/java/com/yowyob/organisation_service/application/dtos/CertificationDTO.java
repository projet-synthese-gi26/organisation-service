package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class CertificationDTO {

    public record Request(
            @NotNull UUID organizationId,
            @NotBlank String name,
            @NotBlank String type,
            String description,
            LocalDateTime obtainementDate
    ) {}

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