package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EmployeeDTO {

    @Schema(name = "EmployeeRequest", description = "Requête de création d'un employé")
    public record Request(
            @NotNull(message = "L'ID de l'organisation est obligatoire")
            @Schema(description = "ID de l'organisation employeur", example = "123e4567-e89b-12d3-a456-426614174000")
            UUID organizationId,

            @Schema(description = "ID de l'utilisateur dans le système d'auth (Keycloak/Auth0...)", example = "auth0|123456")
            UUID authUserId,

            @NotBlank(message = "Le prénom est obligatoire")
            @Schema(example = "Alice")
            String firstName,

            @NotBlank(message = "Le nom est obligatoire")
            @Schema(example = "Merveille")
            String lastName,

            @Schema(description = "Nom complet (optionnel, peut être auto-généré)", example = "Alice Merveille")
            String name,

            @Schema(example = "alice@yowyob.com")
            String email,
            
            @Schema(example = "+237 600 00 00 00")
            String phoneNumber,
            
            String description,
            
            @Schema(example = "F", allowableValues = {"M", "F", "OTHER"})
            String gender,
            
            @Schema(example = "Camerounaise")
            String nationality,
            
            @Schema(example = "1990-01-01")
            LocalDate birthDate,
            
            @Schema(example = "Ingénieur Logiciel")
            String profession,
            
            String biography,

            @Schema(description = "Est-ce un manager ?", defaultValue = "false")
            Boolean isManager,
            
            @Schema(description = "Role ou poste occupé", example = "Lead Developer")
            String role,
            
            @Schema(description = "Département", example = "IT")
            String department,

            @Schema(description = "URI de la photo de profil")
            String photoUri
    ) {}

    @Schema(name = "EmployeeResponse", description = "Réponse Employé enrichie")
    public record Response(
            UUID id,
            String code,
            UUID organizationId,
            UUID authUserId,
            String firstName,
            String lastName,
            String name,
            String email,
            String phoneNumber,
            String role,
            String department,
            Boolean isManager,
            String photoUri,
            String description,
            String gender,
            String nationality,
            LocalDate birthDate,
            String profession,
            
            // Relations polymorphiques
            List<AddressDTO.Response> addresses,
            List<ContactDTO.Response> contacts,
            
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}