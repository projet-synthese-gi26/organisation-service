package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EmployeeDTO {

    @Schema(description = "Requête de création d'un employé")
    public record Request(
            @NotNull(message = "L'ID de l'organisation est obligatoire")
            @Schema(description = "Organisation employeur")
            UUID organizationId,

            @Schema(description = "ID de l'utilisateur dans le système d'auth (Keycloak/Auth0...)", example = "user-uuid-123")
            UUID authUserId,

            @NotBlank(message = "Le prénom est obligatoire")
            String firstName,

            @NotBlank(message = "Le nom est obligatoire")
            String lastName,

            @Schema(description = "Nom complet (optionnel, peut être auto-généré)")
            String name,

            String email,
            String phoneNumber,
            String description,
            String gender,
            String nationality,
            LocalDate birthDate,
            String profession,
            String biography,

            // Champs spécifiques Employee
            @Schema(description = "Est-ce un manager ?", defaultValue = "false")
            Boolean isManager,
            
            @Schema(description = "Role ou poste occupé", example = "Développeur Senior")
            String role,
            
            @Schema(description = "Département", example = "IT")
            String department,

            @Schema(description = "URI de la photo de profil")
            String photoUri
    ) {}

    @Schema(description = "Réponse Employé enrichie")
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