package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ProspectDTO {

    @Schema(description = "Requête création Prospect")
    public record Request(
            @NotNull UUID organizationId,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            @Schema(description = "Niveau d'intérêt", example = "HOT", allowableValues = {"COLD", "WARM", "HOT"})
            String interestLevel
    ) {}

    public record Response(
            UUID id,
            String code,
            UUID organizationId,
            String firstName,
            String lastName,
            String interestLevel,
            
            // Inclusion de l'historique
            List<InteractionDTO.Response> interactions,
            
            // Relations standards
            List<AddressDTO.Response> addresses,
            List<ContactDTO.Response> contacts,
            
            LocalDateTime createdAt
    ) {}
}