package com.yowyob.organisation_service.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AgencyDTO {

    @Schema(description = "Requête de création d'agence")
    public record Request(
            @NotNull(message = "L'ID de l'organisation parente est obligatoire")
            UUID organizationId,

            @Schema(description = "ID du manager (Employé)", nullable = true)
            UUID managerId,

            @NotBlank(message = "Le nom de l'agence est obligatoire")
            String name,

            String shortName,
            String longName,
            String description,
            String location, // Description textuelle rapide (ex: "Centre ville")
            
            @Schema(description = "Est-ce le siège social ?")
            Boolean isHeadquarter,
            
            @Schema(description = "Est-ce une boutique physique ?")
            Boolean isPublic,

            String openTime,
            String closeTime,
            String phone,
            
            @Email
            String email,
            String whatsapp,
            
            String country,
            String city,
            
            @Schema(description = "Chiffre d'affaire moyen")
            BigDecimal averageRevenue,
            
            List<String> keywords
    ) {}

    @Schema(description = "Réponse Agence Enrichie")
    public record Response(
            UUID id,
            String code,
            UUID organizationId,
            UUID managerId,
            String name,
            String shortName,
            String longName,
            Boolean isHeadquarter,
            Boolean isPublic,
            Boolean isActive,
            String location,
            String country,
            String city,
            String openTime,
            String closeTime,
            String email,
            String phone,
            
            // Relations
            List<AddressDTO.Response> addresses,
            List<ContactDTO.Response> contacts,
            
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}