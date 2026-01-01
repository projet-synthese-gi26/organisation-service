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

    @Schema(name = "AgencyRequest", description = "Requête de création d'agence")
    public record Request(
            @NotNull(message = "L'ID de l'organisation parente est obligatoire")
            @Schema(description = "ID de l'organisation", example = "123e4567-e89b-12d3-a456-426614174000")
            UUID organizationId,

            @Schema(description = "ID du manager (Employé)", nullable = true, example = "987e6543-e21b-12d3-a456-426614174000")
            UUID managerId,

            @NotBlank(message = "Le nom de l'agence est obligatoire")
            @Schema(description = "Nom commercial de l'agence", example = "Agence Douala Centre")
            String name,

            @Schema(description = "Nom court", example = "DLA-CTR")
            String shortName,
            
            @Schema(description = "Nom complet officiel", example = "YowYob Douala Centre Ville")
            String longName,
            
            String description,
            
            @Schema(description = "Description textuelle rapide de la localisation", example = "En face de la poste centrale")
            String location,
            
            @Schema(description = "Est-ce le siège social ?", defaultValue = "false")
            Boolean isHeadquarter,
            
            @Schema(description = "Est-ce une boutique physique accessible au public ?", defaultValue = "true")
            Boolean isPublic,

            @Schema(description = "Heure d'ouverture", example = "08:00")
            String openTime,
            
            @Schema(description = "Heure de fermeture", example = "18:00")
            String closeTime,
            
            @Schema(example = "+237 233 42 00 00")
            String phone,
            
            @Email
            @Schema(example = "douala@yowyob.com")
            String email,
            
            @Schema(description = "Numéro Whatsapp Business", example = "+237 699 00 00 00")
            String whatsapp,
            
            @Schema(example = "Cameroun")
            String country,
            
            @Schema(example = "Douala")
            String city,
            
            @Schema(description = "Chiffre d'affaire moyen prévisionnel", example = "5000000.00")
            BigDecimal averageRevenue,
            
            @Schema(description = "Mots-clés pour la recherche", example = "[\"Centre\", \"Siège\"]")
            List<String> keywords
    ) {}

    @Schema(name = "AgencyResponse", description = "Réponse Agence Enrichie")
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