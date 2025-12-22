package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class AddressDTO {

    @Schema(description = "Requête de création d'adresse")
    public record Request(
            @NotNull(message = "L'ID de l'entité parente est obligatoire")
            @Schema(description = "ID de l'organisation, agence ou acteur", example = "uuid-of-organization")
            UUID addressableId,

            @NotBlank(message = "Le type d'entité est obligatoire")
            @Schema(description = "Type de l'entité parente", example = "ORGANIZATION", allowableValues = {"ORGANIZATION", "AGENCY", "ACTOR"})
            String addressableType,

            @Schema(description = "Type d'adresse", example = "HEADQUARTER", allowableValues = {"HEADQUARTER", "BILLING", "DELIVERY"})
            String type,

            @NotBlank(message = "L'adresse ligne 1 est obligatoire")
            String addressLine1,
            
            String addressLine2,

            @NotBlank(message = "La ville est obligatoire")
            String city,
            
            String state,
            String locality,
            String zipCode,
            UUID countryId,
            String poBox,
            String neighborHood,
            String informalDescription,
            Boolean isDefault,
            Double latitude,
            Double longitude
    ) {}

    @Schema(description = "Réponse adresse")
    public record Response(
            UUID id,
            UUID addressableId,
            String addressableType,
            String type,
            String addressLine1,
            String addressLine2,
            String city,
            String state,
            String locality,
            String zipCode,
            UUID countryId,
            String poBox,
            String neighborHood,
            Boolean isDefault,
            Double latitude,
            Double longitude,
            LocalDateTime createdAt
    ) {}
}