package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class BusinessActorDTO {

    @Schema(description = "Requête de création d'un acteur métier (Propriétaire, Partenaire...)")
    public record Request(
            @Schema(description = "ID de l'utilisateur Auth", requiredMode = Schema.RequiredMode.REQUIRED)
            UUID authUserId,
            
            @Schema(description = "ID de l'organisation (si rattaché)", nullable = true)
            UUID organizationId,

            @NotBlank String firstName,
            @NotBlank String lastName,
            String name,
            String email,
            String phoneNumber,
            String description,
            
            // Spécifique BusinessActor
            Boolean isIndividual,
            Boolean isAvailable,
            String type, // Ex: FREELANCE, OWNER
            String role,
            
            @Schema(description = "Liste des qualifications/compétences")
            List<String> qualifications,
            
            @Schema(description = "Méthodes de paiement acceptées")
            List<String> paymentMethods,

            String photoUri,
            String biography
    ) {}

    @Schema(description = "Réponse Business Actor")
    public record Response(
            UUID id,
            String code,
            UUID authUserId,
            UUID organizationId,
            String firstName,
            String lastName,
            String name,
            String email,
            String type,
            String role,
            Boolean isIndividual,
            Boolean isAvailable,
            Boolean isVerified,
            Boolean isActive,
            List<String> qualifications,
            List<String> paymentMethods,
            
            // Relations
            List<AddressDTO.Response> addresses,
            List<ContactDTO.Response> contacts,

            LocalDateTime createdAt
    ) {}
}